package pl.kurs.magdalena_pikulska_test_finalny.services;

import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.metamodel.EntityType;
import jakarta.persistence.metamodel.Metamodel;
import org.springframework.stereotype.Component;
import pl.kurs.magdalena_pikulska_test_finalny.models.Person;

import java.util.HashMap;
import java.util.Map;

@Component
public class PersonTypeRegistry {

    private static final Map<String, Class<? extends Person>> personTypeMap = new HashMap<>();

    @PersistenceContext
    private EntityManager entityManager;

    @PostConstruct
    public void init() {
        Metamodel metamodel = entityManager.getMetamodel();
        for (EntityType<?> entityType : metamodel.getEntities()) {
            Class<?> javaType = entityType.getJavaType();
            if (Person.class.isAssignableFrom(javaType) && !javaType.equals(Person.class)) {
                personTypeMap.put(javaType.getSimpleName().toLowerCase(), (Class<? extends Person>) javaType);
            }
        }
    }

    public Map<String, Class<? extends Person>> getPersonTypeMap() {
        return personTypeMap;
    }
}
