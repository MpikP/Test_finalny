package pl.kurs.magdalena_pikulska_test_finalny.services;

import org.springframework.stereotype.Service;
import pl.kurs.magdalena_pikulska_test_finalny.models.Person;
import pl.kurs.magdalena_pikulska_test_finalny.repositories.PersonRepository;

@Service
public class PersonService extends GenericManagementService<Person, PersonRepository> implements IPersonService<Person> {
    public PersonService(PersonRepository repository) {
        super(repository);
    }
}