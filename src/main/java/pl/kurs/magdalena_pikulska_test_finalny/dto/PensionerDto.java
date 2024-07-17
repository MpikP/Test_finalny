package pl.kurs.magdalena_pikulska_test_finalny.dto;

import org.springframework.stereotype.Component;

@Component
@IDtoType("pensionerdto")
public class PensionerDto extends PersonDto {
    private Double pensionAmount;
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
