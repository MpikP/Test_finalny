package pl.kurs.magdalena_pikulska_test_finalny.models;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;

import java.util.List;

@Entity
public class Employee extends Person implements Identificationable {
    private static final long serialVersionUID = 1L;

    @OneToMany(mappedBy = "employee")
    private List<Employment> employment;

    public Employee() {
    }

    public Employee(List<Employment> employments) {
        this.employment = employments;
    }

    public List<Employment> getEmployments() {
        return employment;
    }

    public void setEmployments(List<Employment> employments) {
        this.employment = employments;
    }
}
