package br.com.erudio.erudioapi.services;

import br.com.erudio.erudioapi.exceptions.ResourceNotFoundException;
import br.com.erudio.erudioapi.models.Person;
import br.com.erudio.erudioapi.repositories.PersonRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.logging.Logger;

@Service
public class PersonService {
    private final Logger logger = Logger.getLogger(PersonService.class.getName());
    private final PersonRepository personRepository;

    public PersonService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    public List<Person> findAll() {
        logger.info("Listing all persons");
        return personRepository.findAll();
    }

    public Person findById(Long id) {
        logger.info("Listing one person");
        return personRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Person not found"));
    }

    public Person create(Person person) {
        logger.info("Creating one person");
        return personRepository.save(person);
    }

    public Person update(Person person) {
        logger.info("Updating one person");
        Person foundPerson = personRepository
                .findById(person.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Person not found"));
        foundPerson.setFirstName(person.getFirstName());
        foundPerson.setLastName(person.getLastName());
        foundPerson.setAddress(person.getAddress());
        foundPerson.setGender(person.getGender());
        return personRepository.save(person);
    }

    public void delete(Long id) {
        logger.info("Deleting one person");
        Person foundPerson = personRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Person not found"));
        personRepository.deleteById(id);
    }
}
