package br.com.erudio.erudioapi.services;

import br.com.erudio.erudioapi.controllers.PersonController;
import br.com.erudio.erudioapi.data.vo.PersonVO;
import br.com.erudio.erudioapi.exceptions.RequiredObjectIsNullException;
import br.com.erudio.erudioapi.exceptions.ResourceNotFoundException;
import br.com.erudio.erudioapi.mapper.DozerMapper;
import br.com.erudio.erudioapi.models.Person;
import br.com.erudio.erudioapi.repositories.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.logging.Logger;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Service
public class PersonService {
    private final Logger logger = Logger.getLogger(PersonService.class.getName());
    @Autowired
    private PersonRepository repository;

    public List<PersonVO> findAll() {
        logger.info("Listing all persons");
        List<PersonVO> persons = DozerMapper.parseListObjects(repository.findAll(), PersonVO.class);
        persons.forEach(person ->
                person.add(linkTo(methodOn(PersonController.class).findById(person.getKey())).withSelfRel())
        );
        return persons;
    }

    public PersonVO findById(Long id) {
        logger.info("Finding one person!");
        Person entity = repository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No records found for this ID!"));
        PersonVO vo = DozerMapper.parseObject(entity, PersonVO.class);
        vo.add(linkTo(methodOn(PersonController.class).findById(id)).withSelfRel());
        return vo;
    }

    public PersonVO create(PersonVO person) {
        logger.info("Creating one person!");
        if (person == null) throw new RequiredObjectIsNullException();
        Person entity = DozerMapper.parseObject(person, Person.class);
        PersonVO vo =  DozerMapper.parseObject(repository.save(entity), PersonVO.class);
        vo.add(linkTo(methodOn(PersonController.class).findById(vo.getKey())).withSelfRel());
        return vo;
    }

    public PersonVO update(PersonVO person) {
        logger.info("Updating one person!");
        if (person == null) throw new RequiredObjectIsNullException();
        Person entity = DozerMapper.parseObject(this.findById(person.getKey()), Person.class);
        entity.setFirstName(person.getFirstName());
        entity.setLastName(person.getLastName());
        entity.setAddress(person.getAddress());
        entity.setGender(person.getGender());
        var vo =  DozerMapper.parseObject(repository.save(entity), PersonVO.class);
        vo.add(linkTo(methodOn(PersonController.class).findById(vo.getKey())).withSelfRel());
        return vo;
    }

    public void delete(Long id) {
        logger.info("Deleting one person");
        this.findById(id);
        repository.deleteById(id);
    }
}
