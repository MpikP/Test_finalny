package pl.kurs.magdalena_pikulska_test_finalny.commands.update;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import pl.kurs.magdalena_pikulska_test_finalny.validators.Vulgarism;

import java.time.LocalDate;

public class UpdateEmploymentCommand {
    @NotNull
    private Long id;
    @NotNull
    private LocalDate startDate;
    private LocalDate endDate;
    @NotBlank
    @Vulgarism
    private String position;
    @NotNull
    @Positive
    private Double salary;
    @NotNull
    private Long idEmployee;

    public Long getId() {
        return id;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public String getPosition() {
        return position;
    }

    public Double getSalary() {
        return salary;
    }

    public Long getIdEmployee() {
        return idEmployee;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public void setSalary(Double salary) {
        this.salary = salary;
    }

    public void setIdEmployee(Long idEmployee) {
        this.idEmployee = idEmployee;
    }
}
