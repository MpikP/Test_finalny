package pl.kurs.magdalena_pikulska_test_finalny.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;

import java.util.Objects;

@Entity
public class Student extends Person implements Identificationable {
    private static final long serialVersionUID = 1L;

    @Column(nullable = false)
    private String graduatedUniversity;
    @Column(nullable = false)
    private Integer studyYear;
    @Column(nullable = false)
    private String studyField;
    @Column(nullable = false)
    private Double scholarshipAmount;

    public String getGraduatedUniversity() {
        return graduatedUniversity;
    }

    public void setGraduatedUniversity(String graduatedUniversity) {
        this.graduatedUniversity = graduatedUniversity;
    }

    public Integer getStudyYear() {
        return studyYear;
    }

    public void setStudyYear(Integer studyYear) {
        this.studyYear = studyYear;
    }

    public String getStudyField() {
        return studyField;
    }

    public void setStudyField(String studyField) {
        this.studyField = studyField;
    }

    public Double getScholarshipAmount() {
        return scholarshipAmount;
    }

    public void setScholarshipAmount(Double scholarshipAmount) {
        this.scholarshipAmount = scholarshipAmount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Student student = (Student) o;
        return Objects.equals(graduatedUniversity, student.graduatedUniversity) && Objects.equals(studyYear, student.studyYear) && Objects.equals(studyField, student.studyField) && Objects.equals(scholarshipAmount, student.scholarshipAmount);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), graduatedUniversity, studyYear, studyField, scholarshipAmount);
    }
}
