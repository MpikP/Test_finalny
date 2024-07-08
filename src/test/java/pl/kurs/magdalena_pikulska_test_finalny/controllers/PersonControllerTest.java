package pl.kurs.magdalena_pikulska_test_finalny.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import pl.kurs.magdalena_pikulska_test_finalny.commands.UpdatePersonCommand;
import pl.kurs.magdalena_pikulska_test_finalny.models.Employee;
import pl.kurs.magdalena_pikulska_test_finalny.models.Pensioner;
import pl.kurs.magdalena_pikulska_test_finalny.models.Person;
import pl.kurs.magdalena_pikulska_test_finalny.models.Student;
import pl.kurs.magdalena_pikulska_test_finalny.services.DynamicManagementService;
import pl.kurs.magdalena_pikulska_test_finalny.services.PersonQueryService;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application-test.properties")
class PersonControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private DynamicManagementService dynamicManagementService;
    @MockBean
    private PersonQueryService personQueryService;


    @Test
    void shouldCreateStudent() throws Exception {
        String shapeJson = "{\"type\":\"student\", ";
        shapeJson = shapeJson + "\"firstName\":\"Jan\", \"lastName\":\"Kowalski\", \"pesel\":\"88111703126\", ";
        shapeJson = shapeJson + "\"height\": 1.82, \"weight\": 60.39, \"emailAddress\":\"j_kowalski@wp.pl\", ";
        shapeJson = shapeJson + "\"graduatedUniversity\":\"Politechnika\", \"studyYear\": 3, \"studyField\":\"Informatyka\", \"scholarshipAmount\": 352.45}";
        Student student = new Student();
        student.setFirstName("Jan");
        student.setLastName("Kowalski");
        student.setPesel("98100103113");
        student.setHeight(1.82);
        student.setWeight(60.38);
        student.setEmailAddress("j_kowalski@wp.pl");
        student.setGraduatedUniversity("Politechnika");
        student.setStudyYear(3);
        student.setStudyField("Informatyka");
        student.setScholarshipAmount(352.45);
        when(dynamicManagementService.addPerson(any(Student.class))).thenReturn(student);


        mockMvc.perform(post("/api/people")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(shapeJson))
                .andExpect(status().isCreated());
    }

    @Test
    void shouldCreatePensioner() throws Exception {
        String shapeJson = "{\"type\":\"pensioner\", ";
        shapeJson = shapeJson + "\"firstName\":\"Jan\", \"lastName\":\"Kowalski\", \"pesel\":\"88111703126\", ";
        shapeJson = shapeJson + "\"height\": 1.82, \"weight\": 60.39, \"emailAddress\":\"j_kowalski@wp.pl\", ";
        shapeJson = shapeJson + "\"pensionAmount\":5200.98, \"workedYear\": 43}";
        Pensioner pensioner = new Pensioner();
        pensioner.setFirstName("Jan");
        pensioner.setLastName("Kowalski");
        pensioner.setPesel("51100103113");
        pensioner.setHeight(1.82);
        pensioner.setWeight(60.38);
        pensioner.setEmailAddress("j_kowalski@wp.pl");
        pensioner.setPensionAmount(5200.98);
        pensioner.setWorkedYear(43);
        when(dynamicManagementService.addPerson(any(Pensioner.class))).thenReturn(pensioner);


        mockMvc.perform(post("/api/people")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(shapeJson))
                .andExpect(status().isCreated());
    }

    @Test
    void shouldCreateEmployee() throws Exception {
        String shapeJson = "{\"type\":\"employee\", ";
        shapeJson = shapeJson + "\"firstName\":\"Jan\", \"lastName\":\"Kowalski\", \"pesel\":\"88111703126\", ";
        shapeJson = shapeJson + "\"height\": 1.82, \"weight\": 60.39, \"emailAddress\":\"j_kowalski@wp.pl\", ";
        shapeJson = shapeJson + "\"pensionAmount\":5200.98, \"workedYear\": 43}";
        Employee employee = new Employee();
        employee.setFirstName("Jan");
        employee.setLastName("Kowalski");
        employee.setPesel("51100103113");
        employee.setHeight(1.82);
        employee.setWeight(60.38);
        employee.setEmailAddress("j_kowalski@wp.pl");
        when(dynamicManagementService.addPerson(any(Employee.class))).thenReturn(employee);


        mockMvc.perform(post("/api/people")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(shapeJson))
                .andExpect(status().isCreated());
    }

    @Test
    void shouldNotCreateEmployee() throws Exception {
        String shapeJson = "{\"type\":\"employee\", ";
        shapeJson = shapeJson + "\"lastName\":\"Kowalski\", \"pesel\":\"88111703123\", ";
        shapeJson = shapeJson + "\"height\": 1.82, \"weight\": 60.39, \"emailAddress\":\"j_kowalski@wp.pl\", ";
        shapeJson = shapeJson + "\"pensionAmount\":5200.98, \"workedYear\": 43}";

        mockMvc.perform(post("/api/people")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(shapeJson))
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldNotCreateStudentxxx() throws Exception {
        String shapeJson = "{\"type\":\"studentxxx\", ";
        shapeJson = shapeJson + "\"firstName\":\"Jan\", \"lastName\":\"Kowalski\", \"pesel\":\"88111703123\", ";
        shapeJson = shapeJson + "\"height\": 1.82, \"weight\": 60.39, \"emailAddress\":\"j_kowalski@wp.pl\", ";
        shapeJson = shapeJson + "\"graduatedUniversity\":\"Politechnika\", \"studyYear\": 3, \"studyField\":\"Informatyka\", \"scholarshipAmount\": 352.45}";

        mockMvc.perform(post("/api/people")

                        .contentType(MediaType.APPLICATION_JSON)
                        .content(shapeJson))
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldGetPeopleByTypeStudentAndParamsStudyYearFrom2() throws Exception {
        String type = "student";
        Integer studyYearFrom = 2;
        int page = 0;
        int size = 10;

        Student student1 = new Student();
        student1.setFirstName("Jan");
        student1.setLastName("Kowalski");
        student1.setPesel("98100103113");
        student1.setHeight(1.82);
        student1.setWeight(60.38);
        student1.setEmailAddress("j_kowalski@wp.pl");
        student1.setGraduatedUniversity("Politechnika");
        student1.setStudyYear(3);
        student1.setStudyField("Informatyka");
        student1.setScholarshipAmount(352.45);


        Student student2 = new Student();
        student2.setFirstName("Marek");
        student2.setLastName("Kot");
        student2.setPesel("98110103113");
        student2.setHeight(1.75);
        student2.setWeight(87.5);
        student2.setEmailAddress("kotek@wp.pl");
        student2.setGraduatedUniversity("Politechnika");
        student2.setStudyYear(4);
        student2.setStudyField("Informatyka");
        student2.setScholarshipAmount(234.45);


        Student student3 = new Student();
        student3.setFirstName("Anna");
        student3.setLastName("Nowak");
        student3.setPesel("98060103113");
        student3.setHeight(1.52);
        student3.setWeight(48.38);
        student3.setEmailAddress("nowakanna@wp.pl");
        student3.setGraduatedUniversity("Politechnika");
        student3.setStudyYear(2);
        student3.setStudyField("Informatyka");
        student3.setScholarshipAmount(452.45);


        Page<Person> pagePerson = (Page<Person>) (Page<?>) new PageImpl<>(Arrays.asList(student1, student2, student3));
        when(personQueryService.getPersonByCriteria(any())).thenReturn(pagePerson);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/people")
                        .param("type", type)
                        .param("studyYearFrom", String.valueOf(studyYearFrom))
                        .param("page", String.valueOf(page))
                        .param("size", String.valueOf(size))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.content").isArray())
                .andExpect(jsonPath("$.content[0].type").value("Student"))
                .andExpect(jsonPath("$.content[0].studyYear").value(3))
                .andExpect(jsonPath("$.content[1].studyYear").value(4));
    }

    @Test
    void shouldGetPeopleByTypeEmployeeAndParamsAgeTo40() throws Exception {
        String type = "employee";
        Integer ageTo = 40;
        int page = 0;
        int size = 10;


        Employee employee1 = new Employee();
        employee1.setFirstName("Jan");
        employee1.setLastName("Kowalski");
        employee1.setPesel("86100103113");
        employee1.setHeight(1.82);
        employee1.setWeight(60.38);
        employee1.setEmailAddress("j_kowalski@wp.pl");

        Employee employee2 = new Employee();
        employee2.setFirstName("Marek");
        employee2.setLastName("Kot");
        employee2.setPesel("88110103113");
        employee2.setHeight(1.75);
        employee2.setWeight(87.5);
        employee2.setEmailAddress("kotek@wp.pl");

        Employee employee3 = new Employee();
        employee3.setFirstName("Anna");
        employee3.setLastName("Nowak");
        employee3.setPesel("90060103113");
        employee3.setHeight(1.52);
        employee3.setWeight(48.38);
        employee3.setEmailAddress("nowakanna@wp.pl");


        Page<Person> pagePerson = (Page<Person>) (Page<?>) new PageImpl<>(Arrays.asList(employee1, employee2, employee3));
        when(personQueryService.getPersonByCriteria(any())).thenReturn(pagePerson);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/people")
                        .param("type", type)
                        .param("ageTo", String.valueOf(ageTo))
                        .param("page", String.valueOf(page))
                        .param("size", String.valueOf(size))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.content").isArray())
                .andExpect(jsonPath("$.content[0].type").value("Employee"))
                .andExpect(jsonPath("$.content[0].pesel").value("86100103113"))
                .andExpect(jsonPath("$.content[1].firstName").value("Marek"));
    }

    @Test
    void shouldUpdatePensionerSuccessfully() throws Exception {
        Long personId = 1L;
        String updatedFirstName = "UpdatedName";

        UpdatePersonCommand updatedPersonCommand = new UpdatePersonCommand();
        updatedPersonCommand.setId(personId);
        updatedPersonCommand.setType("pensioner");
        updatedPersonCommand.setFirstName("Marek");
        updatedPersonCommand.setLastName("Kowal");
        updatedPersonCommand.setPesel("58060103113");
        updatedPersonCommand.setHeight(1.82);
        updatedPersonCommand.setWeight(78.38);
        updatedPersonCommand.setEmailAddress("kowal@wp.pl");
        updatedPersonCommand.setPensionAmount(5598.32);
        updatedPersonCommand.setWorkedYear(34);

        updatedPersonCommand.setFirstName(updatedFirstName);


        Pensioner updatedPerson = new Pensioner();
        updatedPerson.setId(personId);
        updatedPerson.setFirstName("Marek");
        updatedPerson.setLastName("Kowal");
        updatedPerson.setPesel("58060103113");
        updatedPerson.setHeight(1.82);
        updatedPerson.setWeight(78.38);
        updatedPerson.setEmailAddress("kowal@wp.pl");
        updatedPerson.setPensionAmount(5598.32);
        updatedPerson.setWorkedYear(34);

        updatedPerson.setFirstName(updatedFirstName);

        when(dynamicManagementService.updatePerson(any())).thenReturn(updatedPerson);

        mockMvc.perform(MockMvcRequestBuilders.put("/api/people")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedPersonCommand)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value(updatedFirstName));
    }

    @Test
    void shouldHandleOptimisticLockingException() throws Exception {
        Long personId = 1L;
        String updatedFirstName = "UpdatedName";
        UpdatePersonCommand updatedPerson = new UpdatePersonCommand();
        updatedPerson.setId(personId);
        updatedPerson.setType("student");
        updatedPerson.setFirstName("Anna");
        updatedPerson.setLastName("Nowak");
        updatedPerson.setPesel("90060103123");
        updatedPerson.setHeight(1.52);
        updatedPerson.setWeight(48.38);
        updatedPerson.setEmailAddress("nowakanna@wp.pl");

        updatedPerson.setFirstName(updatedFirstName);

        when(dynamicManagementService.updatePerson(any())).thenThrow(org.springframework.dao.OptimisticLockingFailureException.class);

        mockMvc.perform(MockMvcRequestBuilders.put("/api/people")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedPerson)))
                .andExpect(status().isConflict());
    }

}
