package pl.kurs.magdalena_pikulska_test_finalny.commands.create;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import pl.kurs.magdalena_pikulska_test_finalny.validators.Vulgarism;

import java.time.LocalDate;

public class CreateEmploymentCommand {
    @NotNull
    private LocalDate startDate;
    private LocalDate endDate;
    @NotBlank
    @Vulgarism
    private String position;
    @Positive
    private Double salary;
    @NotNull
    private Long idEmployee;

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
}
