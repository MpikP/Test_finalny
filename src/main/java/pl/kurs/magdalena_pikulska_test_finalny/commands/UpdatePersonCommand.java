package pl.kurs.magdalena_pikulska_test_finalny.commands;

import jakarta.validation.constraints.*;
import org.hibernate.validator.constraints.pl.PESEL;
import pl.kurs.magdalena_pikulska_test_finalny.validators.Age;
import pl.kurs.magdalena_pikulska_test_finalny.validators.PersonType;
import pl.kurs.magdalena_pikulska_test_finalny.validators.Vulgarism;


public class UpdatePersonCommand implements IPersonCommand {
    @NotNull
    private Long id;
    @NotBlank
    @PersonType
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


    public Long getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getPesel() {
        return pesel;
    }

    public Double getHeight() {
        return height;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public String getGraduatedUniversity() {
        return graduatedUniversity;
    }

    public Integer getStudyYear() {
        return studyYear;
    }

    public String getStudyField() {
        return studyField;
    }

    public Double getScholarshipAmount() {
        return scholarshipAmount;
    }

    public Double getPensionAmount() {
        return pensionAmount;
    }

    public Integer getWorkedYear() {
        return workedYear;
    }

    public Double getWeight() {
        return weight;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setPesel(String pesel) {
        this.pesel = pesel;
    }

    public void setHeight(Double height) {
        this.height = height;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public void setGraduatedUniversity(String graduatedUniversity) {
        this.graduatedUniversity = graduatedUniversity;
    }

    public void setStudyYear(Integer studyYear) {
        this.studyYear = studyYear;
    }

    public void setStudyField(String studyField) {
        this.studyField = studyField;
    }

    public void setScholarshipAmount(Double scholarshipAmount) {
        this.scholarshipAmount = scholarshipAmount;
    }

    public void setPensionAmount(Double pensionAmount) {
        this.pensionAmount = pensionAmount;
    }

    public void setWorkedYear(Integer workedYear) {
        this.workedYear = workedYear;
    }
}
