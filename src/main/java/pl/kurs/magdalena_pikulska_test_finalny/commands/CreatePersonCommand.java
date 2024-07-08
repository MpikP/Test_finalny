package pl.kurs.magdalena_pikulska_test_finalny.commands;

import jakarta.validation.constraints.*;
import org.hibernate.validator.constraints.pl.PESEL;
import pl.kurs.magdalena_pikulska_test_finalny.validators.Age;
import pl.kurs.magdalena_pikulska_test_finalny.validators.PersonType;
import pl.kurs.magdalena_pikulska_test_finalny.validators.Vulgarism;


public class CreatePersonCommand implements IPersonCommand {
    @PersonType
    @NotBlank
    private String type;
    @NotBlank
    @Vulgarism
    private String firstName;
    @NotBlank
    @Vulgarism
    private String lastName;
    //@PESEL
    @Age
    private String pesel;
    @Positive
    @Max(3)
    private Double height;
    @Positive
    private Double weight;
    @Email
    private String emailAddress;
    @Vulgarism
    private String graduatedUniversity;
    @Positive
    @Max(10)
    private Integer studyYear;
    @Vulgarism
    private String studyField;
    @Positive
    private Double scholarshipAmount;
    @Positive
    private Double pensionAmount;
    @Positive
    @Max(100)
    private Integer workedYear;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firsName) {
        this.firstName = firsName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPesel() {
        return pesel;
    }

    public void setPesel(String pesel) {
        this.pesel = pesel;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

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

    public Double getHeight() {
        return height;
    }

    public void setHeight(Double height) {
        this.height = height;
    }

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }
}
