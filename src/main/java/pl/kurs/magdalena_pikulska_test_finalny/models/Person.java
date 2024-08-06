package pl.kurs.magdalena_pikulska_test_finalny.models;

import jakarta.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.Period;
import java.util.Objects;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Person implements Serializable, Identificationable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String firstName;
    @Column(nullable = false)
    private String lastName;
    //@Column(unique = true, nullable = false)
    private String pesel;
    @Column(nullable = false)
    private Double height;
    @Column(nullable = false)
    private Double weight;
    @Column(nullable = false)
    private String emailAddress;
    @Version
    @Column(nullable = false)
    private Integer version = 1;


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

    public Sex getSex() {

        if (this.pesel == null || this.pesel.length() != 11) {
            return null;
        }

        String substring = this.pesel.substring(9, 10);
        int genderDigit = Integer.parseInt(substring);
        return genderDigit % 2 == 0 ? Sex.WOMAN : Sex.MAN;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public Integer getAge() {
        String[] peselSplit = this.pesel.split("(?!^)");
        LocalDate birthDate = LocalDate.of(getYearOfBirth(peselSplit), getMonthOfBirth(peselSplit), getDayOfBirth(peselSplit));

        LocalDate today = LocalDate.now();
        Period period = Period.between(birthDate, today);
        return period.getYears();

    }


    private static int getDayOfBirth(String[] peselSplit) {
        return Integer.parseInt(peselSplit[4] + peselSplit[5]);
    }


    @Override
    public boolean equals(Object o) {

        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Person person = (Person) o;
        return Objects.equals(id, person.id) && Objects.equals(firstName, person.firstName) && Objects.equals(lastName, person.lastName) && Objects.equals(pesel, person.pesel) && Objects.equals(height, person.height) && Objects.equals(weight, person.weight) && Objects.equals(emailAddress, person.emailAddress) && Objects.equals(version, person.version);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstName, lastName, pesel, height, weight, emailAddress, version);
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
