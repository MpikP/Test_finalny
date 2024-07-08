package pl.kurs.magdalena_pikulska_test_finalny.services;

import org.springframework.stereotype.Service;
import pl.kurs.magdalena_pikulska_test_finalny.models.Employee;
import pl.kurs.magdalena_pikulska_test_finalny.repositories.EmployeeRepository;

@Service
public class EmployeeService extends GenericManagementService<Employee, EmployeeRepository> implements IPersonService<Employee> {

    public EmployeeService(EmployeeRepository repository) {
        super(repository);
    }

}
