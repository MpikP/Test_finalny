package pl.kurs.magdalena_pikulska_test_finalny.dto;

import java.time.LocalDate;

public class EmploymentWithEmployeeDto {
    private Long id;
    private LocalDate startDate;
    private LocalDate endDate;
    private String position;
    private Double salary;
    private PersonDto employee;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public Double getSalary() {
        return salary;
    }

    public void setSalary(Double salary) {
        this.salary = salary;
    }

    public PersonDto getEmployee() {
        return employee;
    }

    public void setEmployee(PersonDto employee) {
        this.employee = employee;
    }
}
