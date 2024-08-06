package pl.kurs.magdalena_pikulska_test_finalny.services.personServices;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.stereotype.Service;
import pl.kurs.magdalena_pikulska_test_finalny.commands.find.FindEmployeeAdditionalFieldsCommand;
import pl.kurs.magdalena_pikulska_test_finalny.dto.EmployeeDto;
import pl.kurs.magdalena_pikulska_test_finalny.models.Employee;
import pl.kurs.magdalena_pikulska_test_finalny.models.Employment;
import pl.kurs.magdalena_pikulska_test_finalny.models.Person;
import pl.kurs.magdalena_pikulska_test_finalny.repositories.EmployeeRepository;
import pl.kurs.magdalena_pikulska_test_finalny.services.*;

import java.time.LocalDate;

@Service
@PersonServiceClass("employee")
public class EmployeeService extends GenericManagementService<Employee, EmployeeRepository> implements IPersonService<Employee> {

    private PersonService personService;
    private EmploymentService employmentService;

    public EmployeeService(EmployeeRepository repository, PersonService personService, EmploymentService employmentService) {
        super(repository);
        this.personService = personService;
        this.employmentService = employmentService;
    }

    public EmployeeDto mapToDto(Employee employee) {
        EmployeeDto employeeDto = new EmployeeDto();
        employeeDto.setId(employee.getId());
        employeeDto.setFirstName(employee.getFirstName());
        employeeDto.setLastName(employee.getLastName());
        employeeDto.setPesel(employee.getPesel());
        employeeDto.setHeight(employee.getHeight());
        employeeDto.setWeight(employee.getWeight());
        employeeDto.setEmailAddress(employee.getEmailAddress());
        employeeDto.setSex();
        employeeDto.setAge();
        employeeDto.setType("Employee");

        Employment currentEmployment = employmentService.getCurrentByEmployeeId(employee.getId());
        Long qtyEmployments = employmentService.countEmploymentsByEmployeeId(employee.getId());
        employeeDto.setCurrentEmployment(employmentService.mapToDto(currentEmployment));
        employeeDto.setQtyEmployments(qtyEmployments);

        return employeeDto;
    }


    public Predicate addAdditionalCriteria(CriteriaBuilder criteriaBuilder, Root<Person> root, Predicate predicate, FindEmployeeAdditionalFieldsCommand command) {

        Root<Employee> employeeRoot = criteriaBuilder.treat(root, Employee.class);
        Join<Employee, Employment> employmentJoin = employeeRoot.join("employment");

        if (command.getEmploymentStartDateFrom() != null) {
            LocalDate fromDateTime = command.getEmploymentStartDateFrom();
            predicate = criteriaBuilder.and(predicate, criteriaBuilder.greaterThanOrEqualTo(employmentJoin.get("startDate"), fromDateTime));
        }

        if (command.getEmploymentStartDateTo() != null) {
            LocalDate toDateTime = command.getEmploymentStartDateTo();
            predicate = criteriaBuilder.and(predicate, criteriaBuilder.lessThanOrEqualTo(employmentJoin.get("startDate"), toDateTime));
        }

        if (command.getPosition() != null) {
            predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(criteriaBuilder.lower(employmentJoin.get("position")), command.getPosition().toLowerCase()));
        }

        if (command.getSalaryFrom() != null) {
            predicate = criteriaBuilder.and(predicate, criteriaBuilder.greaterThanOrEqualTo(employmentJoin.get("salary"), command.getSalaryFrom()));
        }

        if (command.getSalaryTo() != null) {
            predicate = criteriaBuilder.and(predicate, criteriaBuilder.lessThanOrEqualTo(employmentJoin.get("salary"), command.getSalaryTo()));
        }
        return predicate;
    }

}
