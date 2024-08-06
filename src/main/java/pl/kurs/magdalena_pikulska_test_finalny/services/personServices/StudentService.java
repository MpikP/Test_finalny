package pl.kurs.magdalena_pikulska_test_finalny.services.personServices;


import org.springframework.stereotype.Service;
import pl.kurs.magdalena_pikulska_test_finalny.dto.PersonDto;
import pl.kurs.magdalena_pikulska_test_finalny.dto.StudentDto;
import pl.kurs.magdalena_pikulska_test_finalny.models.Student;
import pl.kurs.magdalena_pikulska_test_finalny.repositories.StudentRepository;

@Service
@PersonServiceClass("student")
public class StudentService extends GenericManagementService<Student, StudentRepository> implements IPersonService<Student> {

    private PersonService personService;

    public StudentService(StudentRepository repository, PersonService personService) {
        super(repository);
        this.personService = personService;
    }

    @Override
    public PersonDto mapToDto(Student person) {
        StudentDto studentDto = new StudentDto();
        studentDto.setId(person.getId());
        studentDto.setFirstName(person.getFirstName());
        studentDto.setLastName(person.getLastName());
        studentDto.setPesel(person.getPesel());
        studentDto.setHeight(person.getHeight());
        studentDto.setWeight(person.getWeight());
        studentDto.setEmailAddress(person.getEmailAddress());
        studentDto.setSex();
        studentDto.setAge();
        studentDto.setType("Student");
        studentDto.setGraduatedUniversity(person.getGraduatedUniversity());
        studentDto.setScholarshipAmount(person.getScholarshipAmount());
        studentDto.setStudyField(person.getStudyField());
        studentDto.setStudyYear(person.getStudyYear());
        return studentDto;
    }
}
