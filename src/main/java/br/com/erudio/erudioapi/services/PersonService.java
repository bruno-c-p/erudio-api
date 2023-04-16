package br.com.erudio.erudioapi.services;

import br.com.erudio.erudioapi.data.vo.PersonVO;
import br.com.erudio.erudioapi.exceptions.ResourceNotFoundException;
import br.com.erudio.erudioapi.models.Person;
import br.com.erudio.erudioapi.repositories.PersonRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.logging.Logger;

@Service
public class PersonService {
    private final Logger logger = Logger.getLogger(PersonService.class.getName());
    @Autowired
    private PersonRepository personRepository;
    @Autowired
    private ModelMapper mapper;

    public List<PersonVO> findAll() {
        logger.info("Listing all persons");
        List<Person> persons = personRepository.findAll();
        return persons.stream().map(person -> mapper.map(person, PersonVO.class)).toList();
    }

    public PersonVO findById(Long id) {
        logger.info("Listing one person");
        Person entity = personRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Person not found"));
        return mapper.map(entity, PersonVO.class);
    }

    public PersonVO create(PersonVO person) {
        logger.info("Creating one person");
        Person entity = mapper.map(person, Person.class);
        return mapper.map(personRepository.save(entity), PersonVO.class);
    }

    public PersonVO update(PersonVO person) {
        logger.info("Updating one person");
        Person entity = mapper.map(findById(person.getId()), Person.class);
        entity.setFirstName(person.getFirstName());
        entity.setLastName(person.getLastName());
        entity.setAddress(person.getAddress());
        entity.setGender(person.getGender());
        return mapper.map(personRepository.save(entity), PersonVO.class);
    }

    public void delete(Long id) {
        logger.info("Deleting one person");
        this.findById(id);
        personRepository.deleteById(id);
    }
}
