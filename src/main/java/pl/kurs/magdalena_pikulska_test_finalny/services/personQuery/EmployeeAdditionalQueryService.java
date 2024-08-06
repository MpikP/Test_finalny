package pl.kurs.magdalena_pikulska_test_finalny.services.personQuery;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.stereotype.Service;
import pl.kurs.magdalena_pikulska_test_finalny.commands.find.FindEmployeeAdditionalFieldsCommand;
import pl.kurs.magdalena_pikulska_test_finalny.commands.find.FindPersonAdditionalFieldsCommand;
import pl.kurs.magdalena_pikulska_test_finalny.models.Employee;
import pl.kurs.magdalena_pikulska_test_finalny.models.Employment;
import pl.kurs.magdalena_pikulska_test_finalny.models.Person;

import java.time.LocalDate;

@Service
@AdditionalQueryFind("employee")
public class EmployeeAdditionalQueryService extends AdditionalQueryService implements IAdditionalQueryService {
    @Override
    public Predicate addAdditionalCriteria(CriteriaBuilder criteriaBuilder, Root<Person> root, Predicate predicate, FindPersonAdditionalFieldsCommand personCommand) {

        FindEmployeeAdditionalFieldsCommand command = (FindEmployeeAdditionalFieldsCommand) personCommand;
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
