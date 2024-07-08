package pl.kurs.magdalena_pikulska_test_finalny.dto;


import java.time.LocalDate;

public class EmploymentDto {
    private LocalDate startDate;
    private LocalDate endDate;
    private String position;
    private Double salary;


    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate employmentStartDate) {
        this.startDate = employmentStartDate;
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
}
