package pl.kurs.magdalena_pikulska_test_finalny.dto;

import pl.kurs.magdalena_pikulska_test_finalny.models.Sex;

import java.time.LocalDate;
import java.time.Period;

public class PersonDto {
    private String type;
    private Long id;
    private String firstName;
    private String lastName;
    private String pesel;
    private Double height;
    private Double weight;
    private Integer age;
    private String emailAddress;
    private String sex;


    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

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

    public Integer getAge() {
        return age;
    }

    public void setAge() {
        String[] peselSplit = this.pesel.split("(?!^)");
        LocalDate birthDate = LocalDate.of(getYearOfBirth(peselSplit), getMonthOfBirth(peselSplit), getDayOfBirth(peselSplit));

        LocalDate today = LocalDate.now();
        Period period = Period.between(birthDate, today);
        this.age = period.getYears();

    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getSex() {
        return sex;
    }

    public void setSex() {
        if (this.pesel == null) {
            this.sex = null;
        } else {
            this.sex = Integer.parseInt(this.pesel.substring(9, 10)) % 2 == 0 ? Sex.WOMAN.toString() : Sex.MAN.toString();
        }
    }


    private static int getDayOfBirth(String[] peselSplit) {
        return Integer.parseInt(peselSplit[4] + peselSplit[5]);
    }

    private static int getMonthOfBirth(String[] peselSplit) {
        int monthInt = Integer.parseInt(peselSplit[2] + peselSplit[3]);
        if (monthInt > 80)
            monthInt = -80;
        if (monthInt > 60)
            monthInt = -60;
        if (monthInt > 40)
            monthInt = -40;
        if (monthInt > 20)
            monthInt = -20;
        return monthInt;
    }

    private static int getYearOfBirth(String[] peselSplit) {
        int monthInt = Integer.parseInt(peselSplit[2] + peselSplit[3]);
        int yearInt = Integer.parseInt(peselSplit[0] + peselSplit[1]);

        if (monthInt > 80)
            yearInt += 1800;
        if (monthInt > 60)
            yearInt += 2200;
        if (monthInt > 40)
            yearInt += 2100;
        if (monthInt > 20)
            yearInt += 2000;
        else
            yearInt += 1900;

        return yearInt;
    }


}
