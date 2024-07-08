package pl.kurs.magdalena_pikulska_test_finalny.services;


import org.springframework.stereotype.Service;
import pl.kurs.magdalena_pikulska_test_finalny.models.Student;
import pl.kurs.magdalena_pikulska_test_finalny.repositories.StudentRepository;

@Service
public class StudentService extends GenericManagementService<Student, StudentRepository> implements IPersonService<Student> {
    public StudentService(StudentRepository repository) {
        super(repository);
    }
}
