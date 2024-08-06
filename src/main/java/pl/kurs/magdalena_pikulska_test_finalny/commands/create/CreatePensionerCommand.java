package pl.kurs.magdalena_pikulska_test_finalny.commands.create;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Positive;
import org.springframework.stereotype.Component;

@Component
@CreatePersonType("pensioner")
public class CreatePensionerCommand extends CreatePersonCommonCommand {
    @Positive
    private Double pensionAmount;
    @Positive
    @Max(100)
    private Integer workedYear;

    public Double getPensionAmount() {
        return pensionAmount;
    }

    public void setPensionAmount(Double pensionAmount) {
        this.pensionAmount = pensionAmount;
    }

    public Integer getWorkedYear() {
        return workedYear;
    }

    public void setWorkedYear(Integer workedYear) {
        this.workedYear = workedYear;
    }
}