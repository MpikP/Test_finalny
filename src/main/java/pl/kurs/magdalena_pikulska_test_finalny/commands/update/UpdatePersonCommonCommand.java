package pl.kurs.magdalena_pikulska_test_finalny.commands.update;

import jakarta.validation.constraints.*;
import pl.kurs.magdalena_pikulska_test_finalny.validators.Age;
import pl.kurs.magdalena_pikulska_test_finalny.validators.Vulgarism;

public class UpdatePersonCommonCommand {
    @NotNull
    private Long id;
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
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

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }
}
