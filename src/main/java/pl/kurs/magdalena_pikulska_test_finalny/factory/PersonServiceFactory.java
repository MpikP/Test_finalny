package pl.kurs.magdalena_pikulska_test_finalny.factory;

import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import pl.kurs.magdalena_pikulska_test_finalny.models.*;
import pl.kurs.magdalena_pikulska_test_finalny.services.EmployeeService;
import pl.kurs.magdalena_pikulska_test_finalny.services.PensionerService;
import pl.kurs.magdalena_pikulska_test_finalny.services.IPersonService;
import pl.kurs.magdalena_pikulska_test_finalny.services.StudentService;

@Component
public class PersonServiceFactory {
    private ApplicationContext ctx;

    public PersonServiceFactory(ApplicationContext ctx) {
        this.ctx = ctx;
    }

    public <T extends Identificationable> IPersonService<T> getService(Class<? extends Person> personClass) {
        if (Student.class.equals(personClass)) {
            return (IPersonService<T>) ctx.getBean(StudentService.class);
        } else if (Employee.class.equals(personClass)) {
            return (IPersonService<T>) ctx.getBean(EmployeeService.class);
        } else if (Pensioner.class.equals(personClass)) {
            return (IPersonService<T>) ctx.getBean(PensionerService.class);
        } else {
            throw new IllegalArgumentException("No service found for " + personClass.getSimpleName());
        }
    }
}
