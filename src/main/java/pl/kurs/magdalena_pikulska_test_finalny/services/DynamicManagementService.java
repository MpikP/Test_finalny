package pl.kurs.magdalena_pikulska_test_finalny.services;

import jakarta.persistence.OptimisticLockException;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.stereotype.Service;
import pl.kurs.magdalena_pikulska_test_finalny.factory.PersonServiceFactory;
import pl.kurs.magdalena_pikulska_test_finalny.models.Employment;
import pl.kurs.magdalena_pikulska_test_finalny.models.Identificationable;
import pl.kurs.magdalena_pikulska_test_finalny.models.Person;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class DynamicManagementService {

    private PersonServiceFactory factory;

    public DynamicManagementService(PersonServiceFactory factory) {
        this.factory = factory;
    }

    public <T extends Identificationable> T addPerson(Person person) {
        IPersonService<T> service = factory.getService(person.getClass());
        return service.add((T) person);
    }

    public <T extends Identificationable> Person getPersonById(Person person) {
        IPersonService<T> service = factory.getService(person.getClass());
        return (Person) service.getById(person.getId());
    }

    public <T extends Identificationable> T updatePerson(Person person) {
        try {
            IPersonService<T> service = factory.getService(person.getClass());
            Person existingPerson = (Person) service.getById(person.getId());
            if (!Objects.equals(existingPerson.getVersion(), person.getVersion())) {
                throw new OptimisticLockingFailureException("Data has been modified by another transaction");
            }
            person.setVersion(existingPerson.getVersion());
            return service.edit((T) person);
        } catch (OptimisticLockException e) {
            throw new OptimisticLockingFailureException("Data has been modified by another transaction");
        }
    }

    public <T extends Identificationable> List<T> addAll(List<T> persons) {
        try {
            IPersonService<T> service = factory.getService((Class<? extends Person>) persons.get(0).getClass());
            List<T> addedPersons = new ArrayList<>();

            for (T person : persons) {
                addedPersons.add(service.add(person));
            }

            return addedPersons;
        } catch (OptimisticLockException e) {
            throw new OptimisticLockingFailureException("Data has been modified by another transaction");
        }
    }
}
