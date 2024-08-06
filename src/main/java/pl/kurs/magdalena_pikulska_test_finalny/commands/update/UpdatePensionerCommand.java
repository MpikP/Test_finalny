package pl.kurs.magdalena_pikulska_test_finalny.commands.update;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Positive;
import org.springframework.stereotype.Component;

@Component
@UpdatePersonType("pensioner")
public class UpdatePensionerCommand extends UpdatePersonCommonCommand {
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
