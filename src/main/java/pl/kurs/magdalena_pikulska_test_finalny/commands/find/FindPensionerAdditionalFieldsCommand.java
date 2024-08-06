package pl.kurs.magdalena_pikulska_test_finalny.commands.find;

import org.springframework.stereotype.Component;
import pl.kurs.magdalena_pikulska_test_finalny.commands.IPersonCommand;

import java.util.Map;

@Component
@FindPersonType("pensioner")
public class FindPensionerAdditionalFieldsCommand extends FindPersonAdditionalFieldsCommand {
    private Double pensionAmountFrom;
    private Double pensionAmountTo;
    private Integer workedYearFrom;
    private Integer workedYearTo;

    @Override
    public void mapParams(Map<String, String> params) {
        if (params.containsKey("pensionAmountFrom")) {
            this.pensionAmountFrom = Double.parseDouble(params.get("pensionAmountFrom"));
        }
        if (params.containsKey("pensionAmountTo")) {
            this.pensionAmountTo = Double.parseDouble(params.get("pensionAmountTo"));
        }
        if (params.containsKey("workedYearFrom")) {
            this.workedYearFrom = Integer.parseInt(params.get("workedYearFrom"));
        }
        if (params.containsKey("workedYearTo")) {
            this.workedYearTo = Integer.parseInt(params.get("workedYearTo"));
        }
    }

    public Double getPensionAmountFrom() {
        return pensionAmountFrom;
    }

    public void setPensionAmountFrom(Double pensionAmountFrom) {
        this.pensionAmountFrom = pensionAmountFrom;
    }

    public Double getPensionAmountTo() {
        return pensionAmountTo;
    }

    public void setPensionAmountTo(Double pensionAmountTo) {
        this.pensionAmountTo = pensionAmountTo;
    }

    public Integer getWorkedYearFrom() {
        return workedYearFrom;
    }

    public void setWorkedYearFrom(Integer workedYearFrom) {
        this.workedYearFrom = workedYearFrom;
    }

    public Integer getWorkedYearTo() {
        return workedYearTo;
    }

    public void setWorkedYearTo(Integer workedYearTo) {
        this.workedYearTo = workedYearTo;
    }
}
