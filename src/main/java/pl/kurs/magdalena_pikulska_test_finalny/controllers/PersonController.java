package pl.kurs.magdalena_pikulska_test_finalny.controllers;

import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import pl.kurs.magdalena_pikulska_test_finalny.commands.create.CreatePersonCommand;
import pl.kurs.magdalena_pikulska_test_finalny.commands.find.FindPersonAdditionalFieldsCommand;
import pl.kurs.magdalena_pikulska_test_finalny.commands.find.FindPersonCommand;
import pl.kurs.magdalena_pikulska_test_finalny.commands.update.UpdatePersonCommand;
import pl.kurs.magdalena_pikulska_test_finalny.dto.*;
import pl.kurs.magdalena_pikulska_test_finalny.models.*;
import pl.kurs.magdalena_pikulska_test_finalny.services.DynamicManagementService;
import pl.kurs.magdalena_pikulska_test_finalny.services.personQuery.PersonQueryService;
import pl.kurs.magdalena_pikulska_test_finalny.services.PersonTypeRegistry;

import java.util.Map;

@ComponentScan
@RestController
@RequestMapping("/api/people")
public class PersonController {

    private ModelMapper mapper;
    private DynamicManagementService dynamicManagementService;
    private PersonQueryService personQueryService;
    private PersonTypeRegistry personTypeRegistry;

    public PersonController(ModelMapper mapper, DynamicManagementService dynamicManagementService, PersonQueryService personQueryService, PersonTypeRegistry personTypeRegistry) {
        this.mapper = mapper;
        this.dynamicManagementService = dynamicManagementService;
        this.personQueryService = personQueryService;
        this.personTypeRegistry = personTypeRegistry;
    }


    @GetMapping
    @PreAuthorize("hasRole('ADMIN') or hasRole('EMPLOYEE')")
    ResponseEntity<Page<PersonDto>> getPeopleByTypeAndParams(@Validated @ModelAttribute FindPersonCommand command,
                                                             @RequestParam Map<String, String> additionalParams) {
        String type = command.getType();

        if (type != null)
            command.setAdditionalFieldsCommand(getFindPersonAdditionalFieldsCommand(type, additionalParams));

        Page<Person> peopleByCriteria = personQueryService.getPersonByCriteria(command);
        Page<PersonDto> pagePeopleDto = peopleByCriteria.map(
                f -> convertPersonToPersonDto(f)
        );
        return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(pagePeopleDto);
    }

    @Transactional
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<PersonDto> createPerson(@Validated @RequestBody CreatePersonCommand command) {
        Person newPerson = convertCreateCommandToPerson(command);
        Person addedPerson = dynamicManagementService.addPerson(newPerson);
        Person person = addedPerson;
        PersonDto personDto = convertPersonToPersonDto(person);
        return ResponseEntity.status(HttpStatus.CREATED).body(personDto);
    }


    @PutMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> updatePerson(@Validated @RequestBody UpdatePersonCommand command) {
        Person updatedPerson = convertUpdateCommandToPerson(command);
        Person person = dynamicManagementService.updatePerson(updatedPerson);
        PersonDto personDto = convertPersonToPersonDto(person);
        return ResponseEntity.ok(personDto);
    }


    private Person convertCreateCommandToPerson(CreatePersonCommand command) {
        try {
            Class<? extends Person> personClass = personTypeRegistry.getPersonClassByType(command.getType());
            return mapper.map(command.getPerson(), personClass);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Unknown person type: " + command.getType(), e);
        }
    }


    private Person convertUpdateCommandToPerson(UpdatePersonCommand command) {
        try {
            Class<? extends Person> personClass = personTypeRegistry.getPersonClassByType(command.getType());
            return mapper.map(command.getPerson(), personClass);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Unknown person type: " + command.getType());
        }
    }


    private PersonDto convertPersonToPersonDto(Person person) {
        if (person == null) {
            throw new IllegalArgumentException("Person cannot be null");
        }
        PersonDto personDto = dynamicManagementService.mapToDto(person);
        return personDto;
    }

    private FindPersonAdditionalFieldsCommand getFindPersonAdditionalFieldsCommand(String type, Map<String, String> additionalParams) {

        Class<? extends FindPersonAdditionalFieldsCommand> additionalClass = personTypeRegistry.getFindPersonCommandClassByType(type);

        if (additionalClass != null) {
            FindPersonAdditionalFieldsCommand additionalFieldsCommand;
            try {
                additionalFieldsCommand = additionalClass.getDeclaredConstructor().newInstance();
                additionalFieldsCommand.mapParams(additionalParams);
                return additionalFieldsCommand;
            } catch (Exception e) {
                throw new IllegalArgumentException("Problem with map AdditionalFieldsCommand.");
            }
        } else {
            throw new IllegalArgumentException("Unknown person type: " + type);
        }
    }
}
