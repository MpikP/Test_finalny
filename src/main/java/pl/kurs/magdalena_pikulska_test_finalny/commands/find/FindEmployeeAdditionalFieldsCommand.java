package pl.kurs.magdalena_pikulska_test_finalny.commands.find;


import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Map;

@Component
@FindPersonType("employee")
public class FindEmployeeAdditionalFieldsCommand extends FindPersonAdditionalFieldsCommand {
    private String position;
    private LocalDate employmentStartDateFrom;
    private LocalDate employmentStartDateTo;
    private Double salaryFrom;
    private Double salaryTo;

    @Override
    public void mapParams(Map<String, String> params) {
        if (params.containsKey("position")) {
            this.position = params.get("position");
        }
        if (params.containsKey("salaryFrom")) {
            this.salaryFrom = Double.parseDouble(params.get("salaryFrom"));
        }
        if (params.containsKey("salaryTo")) {
            this.salaryTo = Double.parseDouble(params.get("salaryTo"));
        }
    }


    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public LocalDate getEmploymentStartDateFrom() {
        return employmentStartDateFrom;
    }

    public void setEmploymentStartDateFrom(LocalDate employmentStartDateFrom) {
        this.employmentStartDateFrom = employmentStartDateFrom;
    }

    public LocalDate getEmploymentStartDateTo() {
        return employmentStartDateTo;
    }

    public void setEmploymentStartDateTo(LocalDate employmentStartDateTo) {
        this.employmentStartDateTo = employmentStartDateTo;
    }

    public Double getSalaryFrom() {
        return salaryFrom;
    }

    public void setSalaryFrom(Double salaryFrom) {
        this.salaryFrom = salaryFrom;
    }

    public Double getSalaryTo() {
        return salaryTo;
    }

    public void setSalaryTo(Double salaryTo) {
        this.salaryTo = salaryTo;
    }
}
