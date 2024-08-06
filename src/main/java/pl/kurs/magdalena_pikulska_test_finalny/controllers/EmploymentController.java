package pl.kurs.magdalena_pikulska_test_finalny.controllers;

import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import pl.kurs.magdalena_pikulska_test_finalny.commands.create.CreateEmploymentCommand;
import pl.kurs.magdalena_pikulska_test_finalny.commands.find.FindEmploymentCommand;
import pl.kurs.magdalena_pikulska_test_finalny.commands.update.UpdateEmploymentCommand;
import pl.kurs.magdalena_pikulska_test_finalny.dto.*;
import pl.kurs.magdalena_pikulska_test_finalny.models.Employment;
import pl.kurs.magdalena_pikulska_test_finalny.services.personServices.EmployeeService;
import pl.kurs.magdalena_pikulska_test_finalny.services.EmploymentService;


@ComponentScan
@RestController
@RequestMapping("/api/employments")
public class EmploymentController {
    private ModelMapper mapper;
    private EmploymentService employmentService;
    private EmployeeService employeeService;

    public EmploymentController(ModelMapper mapper, EmploymentService employmentService, EmployeeService employeeService) {
        this.mapper = mapper;
        this.employmentService = employmentService;
        this.employeeService = employeeService;
    }

    @Transactional
    @PostMapping
    @PreAuthorize("hasRole('ADMIN') or hasRole('EMPLOYEE')")
    public ResponseEntity<?> createEmployment(@Validated @RequestBody CreateEmploymentCommand command) {
        Employment newEmployment = mapper.map(command, Employment.class);

        newEmployment.setEmployee(employeeService.getById(command.getIdEmployee()));
        employmentService.addEmployment(newEmployment);
        EmploymentDto employmentDto = mapper.map(newEmployment, EmploymentDto.class);
        return ResponseEntity.status(HttpStatus.CREATED).body(employmentDto);

    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN') or hasRole('EMPLOYEE')")
    ResponseEntity<Page<EmploymentWithEmployeeDto>> getEmploymentsByParams(@Validated @ModelAttribute FindEmploymentCommand command) {
        Page<Employment> employmentsByCriteria = employmentService.getEmploymentByCriteria(command);
        Page<EmploymentWithEmployeeDto> pageEmploymentsDto = employmentsByCriteria.map(
                f -> {
                    EmploymentWithEmployeeDto employmentDto = mapper.map(f, EmploymentWithEmployeeDto.class);
                    PersonDto employeeDto = mapper.map(f.getEmployee(), PersonDto.class);
                    employeeDto.setSex();
                    employeeDto.setAge();
                    employmentDto.setEmployee(employeeDto);
                    return employmentDto;
                }
        );
        return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(pageEmploymentsDto);
    }


    @Transactional
    @PutMapping()
    @PreAuthorize("hasRole('ADMIN') or hasRole('EMPLOYEE')")
    public ResponseEntity<?> updateEmployment(@Validated @RequestBody UpdateEmploymentCommand command) {
        Employment updatedEmployment = mapper.map(command, Employment.class);
        updatedEmployment.setEmployee(employeeService.getById(command.getIdEmployee()));
        Employment employment = employmentService.update(updatedEmployment);
        EmploymentDto employmentDto = mapper.map(employment, EmploymentDto.class);
        return ResponseEntity.ok(employmentDto);
    }


}
