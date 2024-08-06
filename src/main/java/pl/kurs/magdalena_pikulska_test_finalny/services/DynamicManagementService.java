package pl.kurs.magdalena_pikulska_test_finalny.services;

import jakarta.persistence.OptimisticLockException;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.stereotype.Service;
import pl.kurs.magdalena_pikulska_test_finalny.dto.PersonDto;
import pl.kurs.magdalena_pikulska_test_finalny.models.Identificationable;
import pl.kurs.magdalena_pikulska_test_finalny.models.Person;
import pl.kurs.magdalena_pikulska_test_finalny.services.personServices.GenericManagementService;
import pl.kurs.magdalena_pikulska_test_finalny.services.personServices.IPersonService;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class DynamicManagementService {

    private PersonTypeRegistry personTypeRegistry;

    public DynamicManagementService(PersonTypeRegistry personTypeRegistry) {
        this.personTypeRegistry = personTypeRegistry;
    }

    public <T extends Identificationable> T addPerson(Person person) {
        String personType = person.getClass().getSimpleName().toLowerCase();
        Class<? extends GenericManagementService> clazz = personTypeRegistry.getPersonServiceClassByType(personType);
        IPersonService<T> service = personTypeRegistry.getPersonService(clazz);
        return service.add((T) person);
    }

    public <T extends Identificationable> Person getPersonById(Person person) {
        String personType = person.getClass().getSimpleName().toLowerCase();
        Class<? extends GenericManagementService> clazz = personTypeRegistry.getPersonServiceClassByType(personType);
        IPersonService<T> service = personTypeRegistry.getPersonService(clazz);
        return (Person) service.getById(person.getId());
    }

    public <T extends Identificationable> T updatePerson(Person person) {
        try {
            String personType = person.getClass().getSimpleName().toLowerCase();
            Class<? extends GenericManagementService> clazz = personTypeRegistry.getPersonServiceClassByType(personType);
            IPersonService<T> service = personTypeRegistry.getPersonService(clazz);
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
            String personType = persons.get(0).getClass().getSimpleName().toLowerCase();
            Class<? extends GenericManagementService> clazz = personTypeRegistry.getPersonServiceClassByType(personType);
            IPersonService<T> service = personTypeRegistry.getPersonService(clazz);

            List<T> addedPersons = new ArrayList<>();

            for (T person : persons) {
                addedPersons.add(service.add(person));
            }

            return addedPersons;
        } catch (OptimisticLockException e) {
            throw new OptimisticLockingFailureException("Data has been modified by another transaction");
        }
    }

    public PersonDto mapToDto(Person person) {
        Class<? extends GenericManagementService> clazz = personTypeRegistry.getPersonServiceClassByType(person.getClass().getSimpleName().toLowerCase());
        IPersonService service = personTypeRegistry.getPersonService(clazz);
        PersonDto personDto = service.mapToDto(person);
        return personDto;
    }
}
