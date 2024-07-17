package pl.kurs.magdalena_pikulska_test_finalny.dto;

import org.springframework.stereotype.Component;

@Component
@IDtoType("employeedto")
public class EmployeeDto extends PersonDto {
    private EmploymentDto currentEmployment;
    private Long qtyEmployments;

    public EmploymentDto getCurrentEmployment() {
        return currentEmployment;
    }

    public void setCurrentEmployment(EmploymentDto currentEmployment) {
        this.currentEmployment = currentEmployment;
    }

    public Long getQtyEmployments() {
        return qtyEmployments;
    }

    public void setQtyEmployments(Long qtyEmployments) {
        this.qtyEmployments = qtyEmployments;
    }
}
