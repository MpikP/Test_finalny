package pl.kurs.magdalena_pikulska_test_finalny.services.personQuery;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.stereotype.Service;
import pl.kurs.magdalena_pikulska_test_finalny.exceptions.CustomIllegalArgumentException;
import pl.kurs.magdalena_pikulska_test_finalny.models.Person;
import pl.kurs.magdalena_pikulska_test_finalny.services.PersonTypeRegistry;

import java.util.Map;

@Service
public class PersonPredicateClass {
    private PersonTypeRegistry personTypeRegistry;

    public PersonPredicateClass(PersonTypeRegistry personTypeRegistry) {
        this.personTypeRegistry = personTypeRegistry;
    }

    public Predicate getPersonTypePredicate(CriteriaBuilder criteriaBuilder, Root<? extends Person> root, String type) {
        Map<String, Class<? extends Person>> personTypeMap = personTypeRegistry.getPersonTypeMap();
        Class<? extends Person> personClass = personTypeMap.get(type.toLowerCase());
        if (personClass == null) {
            throw new CustomIllegalArgumentException("Type", "null", "Unknown person type: " + type);
        }
        return criteriaBuilder.equal(root.type(), personClass);
    }
}
