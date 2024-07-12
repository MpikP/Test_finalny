package pl.kurs.magdalena_pikulska_test_finalny.services;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.kurs.magdalena_pikulska_test_finalny.models.Person;
import pl.kurs.magdalena_pikulska_test_finalny.repositories.PersonRepository;

import java.util.Map;

@Service
public class PersonService extends GenericManagementService<Person, PersonRepository> implements IPersonService<Person> {
    @Autowired
    private PersonTypeRegistry personTypeRegistry;


    public PersonService(PersonRepository repository) {
        super(repository);
    }

    public Predicate getPersonTypePredicate(CriteriaBuilder criteriaBuilder, Root<? extends Person> root, String type) {
        Map<String, Class<? extends Person>> personTypeMap = personTypeRegistry.getPersonTypeMap();
        Class<? extends Person> personClass = personTypeMap.get(type.toLowerCase());
        if (personClass == null) {
            throw new IllegalArgumentException("Unknown person type: " + type);
        }
        return criteriaBuilder.equal(root.type(), personClass);
    }

}
