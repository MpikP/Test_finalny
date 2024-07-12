package pl.kurs.magdalena_pikulska_test_finalny.controllers;

import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
//import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import pl.kurs.magdalena_pikulska_test_finalny.commands.*;
import pl.kurs.magdalena_pikulska_test_finalny.dto.*;
import pl.kurs.magdalena_pikulska_test_finalny.models.*;
import pl.kurs.magdalena_pikulska_test_finalny.services.DynamicManagementService;
import pl.kurs.magdalena_pikulska_test_finalny.services.EmploymentService;
import pl.kurs.magdalena_pikulska_test_finalny.services.PersonQueryService;

@ComponentScan
@RestController
@RequestMapping("/api/people")
public class PersonController {
    private ModelMapper mapper;
    private DynamicManagementService dynamicManagementService;
    private PersonQueryService personQueryService;
    private EmploymentService employmentService;

    public PersonController(ModelMapper mapper, DynamicManagementService dynamicManagementService, PersonQueryService personQueryService, EmploymentService employmentService) {
        this.mapper = mapper;
        this.dynamicManagementService = dynamicManagementService;
        this.personQueryService = personQueryService;
        this.employmentService = employmentService;
    }

    @GetMapping
    //@PreAuthorize("hasRole('ADMIN') or hasRole('EMPLOYEE')")
    ResponseEntity<Page<PersonDto>> getPeopleByTypeAndParams(@Validated @ModelAttribute FindPersonCommand command) {
        Page<Person> peopleByCriteria = personQueryService.getPersonByCriteria(command);
        Page<PersonDto> pagePeopleDto = peopleByCriteria.map(
                f -> convertPersonToPersonDto(f)
        );
        return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(pagePeopleDto);
    }

    @Transactional
    @PostMapping
    //@PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<PersonDto> createPerson(@Validated @RequestBody CreatePersonCommand command) {
        Person newPerson = convertCommandToPerson(command);
        Person addedPerson = dynamicManagementService.addPerson(newPerson);
        Person person = addedPerson;
        PersonDto personDto = convertPersonToPersonDto(person);
        return ResponseEntity.status(HttpStatus.CREATED).body(personDto);
    }

    @PutMapping
    //@PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> updatePerson(@Validated @RequestBody UpdatePersonCommand command) {
        Person updatedPerson = convertCommandToPerson(command);
        Person person = dynamicManagementService.updatePerson(updatedPerson);
        PersonDto personDto = convertPersonToPersonDto(person);
        return ResponseEntity.ok(personDto);
    }


    private Person convertCommandToPerson(IPersonCommand command) {
        String type = command.getType();
        if (type.equalsIgnoreCase("student")) {
            return mapper.map(command, Student.class);
        } else if (type.equalsIgnoreCase("employee")) {
            return mapper.map(command, Employee.class);
        } else if (type.equalsIgnoreCase("pensioner")) {
            return mapper.map(command, Pensioner.class);
        } else {
            throw new IllegalArgumentException("Unknown person type: " + type);
        }
    }

    private PersonDto convertPersonToPersonDto(Person person) {

        if (person == null) {
            throw new IllegalArgumentException("Person cannot be null");
        }

        String type = person.getClass().getSimpleName();
        if (type.equalsIgnoreCase("employee")) {
            Employee employee = mapper.map(person, Employee.class);
            Employment currentEmployment = employmentService.getCurrentByEmployeeId(employee.getId());
            Long qtyEmployments = employmentService.countEmploymentsByEmployeeId(employee.getId());
            EmployeeDto employeeDto = mapper.map(person, EmployeeDto.class);
            EmploymentDto currentEmploymentDto = mapper.map(currentEmployment, EmploymentDto.class);
            employeeDto.setCurrentEmployment(currentEmploymentDto);
            employeeDto.setQtyEmployments(qtyEmployments);
            employeeDto.setType("Employee");
            setCommonFields(employeeDto);
            return employeeDto;
        } else if (type.equalsIgnoreCase("student")) {
            StudentDto studentDto = mapper.map(person, StudentDto.class);
            studentDto.setType("Student");
            setCommonFields(studentDto);
            return studentDto;
        } else if (type.equalsIgnoreCase("pensioner")) {
            PensionerDto pensionerDto = mapper.map(person, PensionerDto.class);
            pensionerDto.setType("Pensioner");
            setCommonFields(pensionerDto);
            return pensionerDto;
        } else {
            throw new IllegalArgumentException("Unknown person type: " + type);
        }

    }

    private void setCommonFields(PersonDto personDto) {
        personDto.setSex();
        personDto.setAge();
    }

}
