package pl.kurs.magdalena_pikulska_test_finalny.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;

import java.util.Objects;

@Entity
public class Pensioner extends Person implements Identificationable {
    private static final long serialVersionUID = 1L;

    @Column(nullable = false)
    private Double pensionAmount;
    @Column(nullable = false)
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Pensioner pensioner = (Pensioner) o;
        return Objects.equals(pensionAmount, pensioner.pensionAmount) && Objects.equals(workedYear, pensioner.workedYear);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), pensionAmount, workedYear);
    }
}
