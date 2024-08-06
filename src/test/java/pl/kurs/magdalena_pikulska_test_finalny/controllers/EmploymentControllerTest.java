package pl.kurs.magdalena_pikulska_test_finalny.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import pl.kurs.magdalena_pikulska_test_finalny.commands.update.UpdateEmploymentCommand;
import pl.kurs.magdalena_pikulska_test_finalny.exceptions.CustomIllegalArgumentException;
import pl.kurs.magdalena_pikulska_test_finalny.models.Employee;
import pl.kurs.magdalena_pikulska_test_finalny.models.Employment;
import pl.kurs.magdalena_pikulska_test_finalny.services.personServices.EmployeeService;
import pl.kurs.magdalena_pikulska_test_finalny.services.EmploymentService;
import org.junit.jupiter.api.Assertions;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application-test.properties")
class EmploymentControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;


    @MockBean
    private EmploymentService employmentService;
    @MockBean
    private EmployeeService employeeService;


    @Test
    void shouldCreateEmployment() throws Exception {
        String shapeJson = "{\"startDate\":\"2023-12-15\", \"position\":\"Ekspert\", \"salary\":7543.21, \"idEmployee\":95}";

        Employment employment = new Employment();
        employment.setStartDate(LocalDate.of(2023, 12, 15));
        employment.setPosition("Ekspert");
        employment.setSalary(7543.21);
        employment.setEmployee(new Employee());

        when(employeeService.getById(any(Long.class))).thenReturn(new Employee());
        when(employmentService.add(any(Employment.class))).thenReturn(employment);


        mockMvc.perform(post("/api/employments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(shapeJson))
                .andExpect(status().isCreated());
    }

    @Test
    void shouldNotCreateEmploymentSalaryLessThen0() throws Exception {
        String shapeJson = "{\"startDate\":\"2023-12-15\", \"position\":\"Ekspert\", \"salary\":-7543.21, \"idEmployee\":95}";

        mockMvc.perform(post("/api/employments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(shapeJson))
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldGetAllEmployment() throws Exception {

        Employee employee = new Employee();
        employee.setId(1000000000l);
        employee.setFirstName("Jan");
        employee.setLastName("Kowalski");
        employee.setPesel("86100103113");
        employee.setHeight(1.82);
        employee.setWeight(60.38);
        employee.setEmailAddress("j_kowalski@wp.pl");

        Employment employment1 = new Employment();
        employment1.setStartDate(LocalDate.of(2023, 12, 15));
        employment1.setPosition("Ekspert");
        employment1.setSalary(12543.21);
        employment1.setEmployee(employee);

        Employment employment2 = new Employment();
        employment2.setStartDate(LocalDate.of(2023, 12, 1));
        employment2.setPosition("Specjalista");
        employment2.setSalary(7543.21);
        employment2.setEmployee(employee);

        Employment employment3 = new Employment();
        employment3.setStartDate(LocalDate.of(2024, 1, 3));
        employment3.setPosition("Junior");
        employment3.setSalary(5543.21);
        employment3.setEmployee(employee);

        List<Employment> employments = Arrays.asList(employment1, employment2, employment3);
        Pageable pageable = PageRequest.of(0, 10);

        Page<Employment> pageEmployment = new PageImpl<>(employments, pageable, employments.size());

        when(employeeService.getById(any(Long.class))).thenReturn(employee);
        when(employmentService.getEmploymentByCriteria(any())).thenReturn(pageEmployment);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/employments")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.content").isArray())
                .andExpect(jsonPath("$.content[0].position").value("Ekspert"))
                .andExpect(jsonPath("$.content[1].salary").value(7543.21))
                .andExpect(jsonPath("$.content[2].position").value("Junior"));
    }

    @Test
    void shouldUpdateEmploymentSuccessfully() throws Exception {
        LocalDate updatedEndDate = LocalDate.of(2024, 6, 30);

        UpdateEmploymentCommand updateEmploymentCommand = new UpdateEmploymentCommand();
        updateEmploymentCommand.setId(1L);
        updateEmploymentCommand.setPosition("Ekspert");
        updateEmploymentCommand.setStartDate(LocalDate.of(2024, 1, 3));
        updateEmploymentCommand.setSalary(12543.21);
        updateEmploymentCommand.setIdEmployee(2L);

        updateEmploymentCommand.setEndDate(updatedEndDate);

        Employee employee = new Employee();
        employee.setId(2L);
        employee.setFirstName("Jan");
        employee.setLastName("Kowalski");
        employee.setPesel("86100103113");
        employee.setHeight(1.82);
        employee.setWeight(60.38);
        employee.setEmailAddress("j_kowalski@wp.pl");

        Employment employment = new Employment();
        employment.setStartDate(LocalDate.of(2024, 1, 3));
        employment.setPosition("Ekspert");
        employment.setSalary(12543.21);
        employment.setEmployee(employee);

        employment.setEndDate(updatedEndDate);


        when(employmentService.update(any())).thenReturn(employment);

        mockMvc.perform(MockMvcRequestBuilders.put("/api/employments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateEmploymentCommand)))
                .andExpect(status().isOk());
//                .andExpect(jsonPath("$.endDate").value("2024-06-30"));
    }

    @Test
    void shouldHandleOptimisticLockingException() throws Exception {
        LocalDate updatedEndDate = LocalDate.of(2024, 6, 30);

        UpdateEmploymentCommand updateEmploymentCommand = new UpdateEmploymentCommand();
        updateEmploymentCommand.setId(1L);
        updateEmploymentCommand.setPosition("Ekspert");
        updateEmploymentCommand.setStartDate(LocalDate.of(2024, 1, 3));
        updateEmploymentCommand.setSalary(12543.21);
        updateEmploymentCommand.setIdEmployee(2L);
        updateEmploymentCommand.setEndDate(updatedEndDate);

        when(employeeService.getById(any())).thenReturn(new Employee());

        when(employmentService.update(any())).thenThrow(org.springframework.dao.OptimisticLockingFailureException.class);

        mockMvc.perform(MockMvcRequestBuilders.put("/api/employments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateEmploymentCommand)))
                .andExpect(status().isConflict());
    }

    @Test
    public void addEmploymentShouldHandleandlesOptimisticLockException() {

        Employment employment = new Employment();

        doThrow(OptimisticLockingFailureException.class).when(employmentService).edit(any());

        Assertions.assertThrows(OptimisticLockingFailureException.class, () -> {
            employmentService.edit(employment);
        });

        verify(employmentService, times(1)).edit(any());

    }

    @Test
    public void shouldThrowCustomIllegalArgumentExceptionWhenDatesAreNotCorrect() {
        Employment employment = new Employment();
        employment.setStartDate(LocalDate.of(2024, 1, 1));
        when(employmentService.ifDatesAreNotCorrect(1L, LocalDate.of(2024, 1, 1)))
                .thenReturn(true);

        doThrow(CustomIllegalArgumentException.class).when(employmentService).addEmployment(any());

        CustomIllegalArgumentException thrownException = Assertions.assertThrows(
                CustomIllegalArgumentException.class,
                () -> employmentService.addEmployment(employment)
        );

        verify(employmentService, times(1)).addEmployment(any());
    }

    @Test
    public void shouldThrowOptimisticLockingFailureExceptionWhenVersionDoesNotMatch() {
        // Given
        Employment currentEmployment = new Employment();
        currentEmployment.setId(1L);
        currentEmployment.setEndDate(null);
        currentEmployment.setVersion(1);

        Employment newEmployment = new Employment();
        newEmployment.setStartDate(LocalDate.of(2024, 1, 2));
        newEmployment.setEmployee(new Employee());
        newEmployment.setVersion(2);

        when(employmentService.getCurrentByEmployeeId(1L)).thenReturn(currentEmployment);
        when(employmentService.getById(1L)).thenReturn(currentEmployment);

        doThrow(OptimisticLockingFailureException.class).when(employmentService).addEmployment(any());

        OptimisticLockingFailureException thrownException = Assertions.assertThrows(
                OptimisticLockingFailureException.class,
                () -> employmentService.addEmployment(newEmployment)
        );

        verify(employmentService, times(1)).addEmployment(any());
    }

    @Test
    public void shouldHandleDateOverlap() {
        Employee employee = new Employee();
        employeeService.add(employee);

        Employment existingEmployment = new Employment();
        existingEmployment.setEmployee(employee);
        existingEmployment.setStartDate(LocalDate.of(2024, 2, 1));
        existingEmployment.setEndDate(null);
        employmentService.add(existingEmployment);

        Employment newEmployment = new Employment();
        newEmployment.setEmployee(employee);
        newEmployment.setStartDate(LocalDate.of(2024, 1, 2));

        doThrow(CustomIllegalArgumentException.class).when(employmentService).addEmployment(any());
        CustomIllegalArgumentException thrownException = Assertions.assertThrows(
                CustomIllegalArgumentException.class,
                () -> employmentService.addEmployment(newEmployment)
        );

        verify(employmentService, times(1)).addEmployment(any());
    }
}