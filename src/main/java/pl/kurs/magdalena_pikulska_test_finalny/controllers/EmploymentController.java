package pl.kurs.magdalena_pikulska_test_finalny.controllers;

import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import pl.kurs.magdalena_pikulska_test_finalny.commands.CreateEmploymentCommand;
import pl.kurs.magdalena_pikulska_test_finalny.commands.FindEmploymentCommand;
import pl.kurs.magdalena_pikulska_test_finalny.commands.UpdateEmploymentCommand;
import pl.kurs.magdalena_pikulska_test_finalny.dto.*;
import pl.kurs.magdalena_pikulska_test_finalny.models.Employment;
import pl.kurs.magdalena_pikulska_test_finalny.services.EmployeeService;
import pl.kurs.magdalena_pikulska_test_finalny.services.EmploymentService;
import pl.kurs.magdalena_pikulska_test_finalny.services.PersonService;


@ComponentScan
@RestController
//@PreAuthorize("hasRole('ADMIN', 'EMPLOYEE')"
@RequestMapping("/api/employments")
public class EmploymentController {
    private ModelMapper mapper;
    private EmploymentService employmentService;
    private EmployeeService employeeService;
    private PersonService personService;

    public EmploymentController(ModelMapper mapper, EmploymentService employmentService, EmployeeService employeeService, PersonService personService) {
        this.mapper = mapper;
        this.employmentService = employmentService;
        this.employeeService = employeeService;
        this.personService = personService;
    }

    @Transactional
    @PostMapping
    public ResponseEntity<?> createEmployment(@Validated @RequestBody CreateEmploymentCommand command) {
        Employment newEmployment = mapper.map(command, Employment.class);

        newEmployment.setEmployee(employeeService.getById(command.getIdEmployee()));
        employmentService.addEmployment(newEmployment);
        EmploymentDto employmentDto = mapper.map(newEmployment, EmploymentDto.class);
        return ResponseEntity.status(HttpStatus.CREATED).body(employmentDto);

    }

    @GetMapping
    ResponseEntity<Page<EmploymentWithEmployeeDto>> getEmploymentsByParams(@Validated @ModelAttribute FindEmploymentCommand command) {
        Page<Employment> employmentsByCriteria = employmentService.getEmploymentByCriteria(command);
        Page<EmploymentWithEmployeeDto> pageEmploymentsDto = employmentsByCriteria.map(
                f -> {
                    EmploymentWithEmployeeDto employmentDto = mapper.map(f, EmploymentWithEmployeeDto.class);
                    PersonDto employeeDto = mapper.map(f.getEmployee(), PersonDto.class);
                    employeeDto.setSex();
                    employeeDto.setAge();
                    employmentDto.setEmployeeDto(employeeDto);
                    return employmentDto;
                }
        );
        return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(pageEmploymentsDto);
    }


    @Transactional
    @PutMapping()
    public ResponseEntity<?> updateEmployment(@Validated @RequestBody UpdateEmploymentCommand command) {
        Employment updatedEmployment = mapper.map(command, Employment.class);
        updatedEmployment.setEmployee(employeeService.getById(command.getIdEmployee()));
        Employment employment = employmentService.update(updatedEmployment);
        EmploymentDto employmentDto = mapper.map(employment, EmploymentDto.class);
        return ResponseEntity.ok(employmentDto);
    }


}
