package pl.kurs.magdalena_pikulska_test_finalny.services;

import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.metamodel.EntityType;
import jakarta.persistence.metamodel.Metamodel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import pl.kurs.magdalena_pikulska_test_finalny.dto.IDtoType;
import pl.kurs.magdalena_pikulska_test_finalny.dto.PersonDto;
import pl.kurs.magdalena_pikulska_test_finalny.models.Person;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Component
public class PersonTypeRegistry {

    private static final Map<String, Class<? extends Person>> personTypeMap = new HashMap<>();
    private static final Map<String, Class<? extends PersonDto>> personDtoTypeMap = new HashMap<>();

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private ApplicationContext applicationContext;

    @PostConstruct
    public void init() {
        Metamodel metamodel = entityManager.getMetamodel();
        for (EntityType<?> entityType : metamodel.getEntities()) {
            Class<?> javaType = entityType.getJavaType();
            if (Person.class.isAssignableFrom(javaType) && !javaType.equals(Person.class)) {
                personTypeMap.put(javaType.getSimpleName().toLowerCase(), (Class<? extends Person>) javaType);
            }
        }

        Map<String, Object> beansWithAnnotation = applicationContext.getBeansWithAnnotation(IDtoType.class);
        for (Object bean : beansWithAnnotation.values()) {
            Class<?> dtoClass = bean.getClass();
            IDtoType annotation = dtoClass.getAnnotation(IDtoType.class);
            if (annotation != null) {
                String dtoType = annotation.value();
                personDtoTypeMap.put(dtoType.toLowerCase(), (Class<? extends PersonDto>) dtoClass);
            }
        }
    }

    public Map<String, Class<? extends Person>> getPersonTypeMap() {
        return personTypeMap;
    }

    public Class<? extends Person> getPersonClassByType(String type) {
        Class<? extends Person> personClass = personTypeMap.get(type.toLowerCase());
        if (personClass == null) {
            throw new IllegalArgumentException("Unknown person type: " + type);
        }
        return personClass;
    }

    public Class<? extends PersonDto> getPersonDtoClassByType(String type) {
        String typeDto = type.toLowerCase() + "dto";
        Class<? extends PersonDto> personDtoClass = personDtoTypeMap.get(typeDto);
        if (personDtoClass == null) {
            throw new IllegalArgumentException("Unknown person DTO type: " + type);
        }
        return personDtoClass;
    }
}
