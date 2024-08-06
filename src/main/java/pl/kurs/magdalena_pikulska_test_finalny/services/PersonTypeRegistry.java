package pl.kurs.magdalena_pikulska_test_finalny.services;

import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityManager;
import jakarta.persistence.metamodel.EntityType;
import jakarta.persistence.metamodel.Metamodel;
import org.springframework.aop.framework.AopProxyUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import pl.kurs.magdalena_pikulska_test_finalny.commands.create.CreatePersonCommonCommand;
import pl.kurs.magdalena_pikulska_test_finalny.commands.create.CreatePersonType;
import pl.kurs.magdalena_pikulska_test_finalny.commands.find.FindPersonAdditionalFieldsCommand;
import pl.kurs.magdalena_pikulska_test_finalny.commands.find.FindPersonType;
import pl.kurs.magdalena_pikulska_test_finalny.commands.update.UpdatePersonCommonCommand;
import pl.kurs.magdalena_pikulska_test_finalny.commands.update.UpdatePersonType;
import pl.kurs.magdalena_pikulska_test_finalny.dto.PersonDtoType;
import pl.kurs.magdalena_pikulska_test_finalny.dto.PersonDto;
import pl.kurs.magdalena_pikulska_test_finalny.exceptions.CustomIllegalArgumentException;
import pl.kurs.magdalena_pikulska_test_finalny.models.*;
import pl.kurs.magdalena_pikulska_test_finalny.services.personQuery.AdditionalQueryFind;
import pl.kurs.magdalena_pikulska_test_finalny.services.personQuery.AdditionalQueryService;
import pl.kurs.magdalena_pikulska_test_finalny.services.personQuery.IAdditionalQueryService;
import pl.kurs.magdalena_pikulska_test_finalny.services.personServices.GenericManagementService;
import pl.kurs.magdalena_pikulska_test_finalny.services.personServices.IPersonService;
import pl.kurs.magdalena_pikulska_test_finalny.services.personServices.PersonServiceClass;

import java.util.HashMap;
import java.util.Map;

@Component
public class PersonTypeRegistry {

    private static final Map<String, Class<? extends Person>> personTypeMap = new HashMap<>();
    private static final Map<String, Class<? extends PersonDto>> personDtoTypeMap = new HashMap<>();
    private static final Map<String, Class<? extends GenericManagementService>> personServiceTypeMap = new HashMap<>();
    private static final Map<String, Class<? extends AdditionalQueryService>> additionQueryServiceTypeMap = new HashMap<>();
    private static final Map<String, Class<? extends CreatePersonCommonCommand>> createPersonCommand = new HashMap<>();
    private static final Map<String, Class<? extends UpdatePersonCommonCommand>> updatePersonCommand = new HashMap<>();
    private static final Map<String, Class<? extends FindPersonAdditionalFieldsCommand>> findPersonCommand = new HashMap<>();

    private EntityManager entityManager;
    private ApplicationContext applicationContext;

    public PersonTypeRegistry(EntityManager entityManager, ApplicationContext applicationContext) {
        this.entityManager = entityManager;
        this.applicationContext = applicationContext;
    }

    @PostConstruct
    public void init() {
        Metamodel metamodel = entityManager.getMetamodel();
        for (EntityType<?> entityType : metamodel.getEntities()) {
            Class<?> javaType = entityType.getJavaType();
            if (Person.class.isAssignableFrom(javaType) && !javaType.equals(Person.class)) {
                personTypeMap.put(javaType.getSimpleName().toLowerCase(), (Class<? extends Person>) javaType);
            }
        }

        Map<String, Object> beansWithAnnotation = applicationContext.getBeansWithAnnotation(PersonDtoType.class);
        for (Object bean : beansWithAnnotation.values()) {
            Class<?> dtoClass = bean.getClass();
            PersonDtoType annotation = dtoClass.getAnnotation(PersonDtoType.class);
            if (annotation != null) {
                String dtoType = annotation.value();
                personDtoTypeMap.put(dtoType.toLowerCase(), (Class<? extends PersonDto>) dtoClass);
            }
        }

        Map<String, Object> beansPersonService = applicationContext.getBeansWithAnnotation(PersonServiceClass.class);
        for (Object bean : beansPersonService.values()) {
            Class<?> c = bean.getClass();
            Class<?> targetClass = AopProxyUtils.ultimateTargetClass(bean);
            PersonServiceClass annotation = targetClass.getAnnotation(PersonServiceClass.class);
            if (annotation != null) {
                String serviceType = annotation.value();
                personServiceTypeMap.put(serviceType.toLowerCase(), (Class<? extends GenericManagementService>) c);
            }
        }

        Map<String, Object> beansAdditionQuery = applicationContext.getBeansWithAnnotation(AdditionalQueryFind.class);
        for (Object bean : beansAdditionQuery.values()) {
            Class<?> c = bean.getClass();
            AdditionalQueryFind annotation = c.getAnnotation(AdditionalQueryFind.class);
            if (annotation != null) {
                String dtoType = annotation.value();
                additionQueryServiceTypeMap.put(dtoType.toLowerCase(), (Class<? extends AdditionalQueryService>) c);
            }
        }

        Map<String, Object> beansCreatePersonCommand = applicationContext.getBeansWithAnnotation(CreatePersonType.class);
        for (Object bean : beansCreatePersonCommand.values()) {
            Class<?> c = bean.getClass();
            CreatePersonType annotation = c.getAnnotation(CreatePersonType.class);
            if (annotation != null) {
                String type = annotation.value();
                createPersonCommand.put(type.toLowerCase(), (Class<? extends CreatePersonCommonCommand>) c);
            }
        }

        Map<String, Object> beansUpdatePersonCommand = applicationContext.getBeansWithAnnotation(UpdatePersonType.class);
        for (Object bean : beansUpdatePersonCommand.values()) {
            Class<?> c = bean.getClass();
            UpdatePersonType annotation = c.getAnnotation(UpdatePersonType.class);
            if (annotation != null) {
                String type = annotation.value();
                updatePersonCommand.put(type.toLowerCase(), (Class<? extends UpdatePersonCommonCommand>) c);
            }
        }

        Map<String, Object> beansFindPersonCommand = applicationContext.getBeansWithAnnotation(FindPersonType.class);
        for (Object bean : beansFindPersonCommand.values()) {
            Class<?> c = bean.getClass();
            FindPersonType annotation = c.getAnnotation(FindPersonType.class);
            if (annotation != null) {
                String type = annotation.value();
                findPersonCommand.put(type.toLowerCase(), (Class<? extends FindPersonAdditionalFieldsCommand>) c);
            }
        }
    }

    public Map<String, Class<? extends Person>> getPersonTypeMap() {
        return personTypeMap;
    }

    public Class<? extends Person> getPersonClassByType(String type) {
        Class<? extends Person> personClass = personTypeMap.get(type.toLowerCase());
        if (personClass == null) {
            throw new CustomIllegalArgumentException("Type", type, "Unknown person type: " + type);
        }
        return personClass;
    }

    public Class<? extends PersonDto> getPersonDtoClassByType(String type) {
        String typeDto = type.toLowerCase() + "dto";
        Class<? extends PersonDto> personDtoClass = personDtoTypeMap.get(typeDto);
        if (personDtoClass == null) {
            throw new CustomIllegalArgumentException("Type", type, "Unknown person DTO type: " + type);
        }
        return personDtoClass;
    }

    public Class<? extends GenericManagementService> getPersonServiceClassByType(String type) {
        Class<? extends GenericManagementService> clazz = personServiceTypeMap.get(type.toLowerCase());
        if (clazz == null) {
            throw new CustomIllegalArgumentException("Type", type, "Unknown service type: " + type);
        }
        return clazz;
    }

    public Class<? extends AdditionalQueryService> getAdditionalQueryServiceClassByType(String type) {
        Class<? extends AdditionalQueryService> clazz = additionQueryServiceTypeMap.get(type.toLowerCase());
        if (clazz == null) {
            throw new CustomIllegalArgumentException("Type", type, "Unknown additional query type: " + type);
        }
        return clazz;
    }

    public <T extends GenericManagementService> IPersonService getPersonService(Class<? extends GenericManagementService> clazz) {
        return (IPersonService) applicationContext.getBean(clazz);
    }

    public <T extends AdditionalQueryService> IAdditionalQueryService getAdditionQueryService(Class<? extends AdditionalQueryService> clazz) {
        return (IAdditionalQueryService) applicationContext.getBean(clazz);
    }

    public Class<? extends CreatePersonCommonCommand> getCreatePersonCommandClassByType(String type) {
        Class<? extends CreatePersonCommonCommand> clazz = createPersonCommand.get(type.toLowerCase());
        if (clazz == null) {
            throw new CustomIllegalArgumentException("Type", type, "Unknown create person command type: " + type);
        }
        return clazz;
    }


    public Class<? extends UpdatePersonCommonCommand> getUpdatePersonCommandClassByType(String type) {
        Class<? extends UpdatePersonCommonCommand> clazz = updatePersonCommand.get(type.toLowerCase());
        if (clazz == null) {
            throw new CustomIllegalArgumentException("Type", type, "Unknown update person command type: " + type);
        }
        return clazz;
    }


    public Class<? extends FindPersonAdditionalFieldsCommand> getFindPersonCommandClassByType(String type) {
        Class<? extends FindPersonAdditionalFieldsCommand> clazz = findPersonCommand.get(type.toLowerCase());
        if (clazz == null) {
            throw new CustomIllegalArgumentException("Type", type, "Unknown find person command type: " + type);
        }
        return clazz;
    }

}
