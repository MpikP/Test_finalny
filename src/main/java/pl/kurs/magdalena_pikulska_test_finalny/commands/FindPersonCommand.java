package pl.kurs.magdalena_pikulska_test_finalny.commands;


import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import pl.kurs.magdalena_pikulska_test_finalny.validators.Pagination;
import pl.kurs.magdalena_pikulska_test_finalny.validators.PersonType;

import java.time.LocalDate;

@Pagination
public class FindPersonCommand implements IPaginationCommand {
    @PersonType
    private String type;
    private Long id;
    private String firstName;
    private String lastName;
    private String pesel;
    private String emailAddress;
    private String sex;
    private String graduatedUniversity;
    private Integer studyYear;
    private String studyField;
    private String position;
    private Double heightFrom;
    private Double heightTo;
    private Integer ageFrom;
    private Integer ageTo;
    private LocalDate employmentStartDateFrom;
    private LocalDate employmentStartDateTo;
    private Integer studyYearFrom;
    private Integer studyYearTo;
    private Double scholarshipAmountFrom;
    private Double scholarshipAmountTo;
    private Double salaryFrom;
    private Double salaryTo;
    private Double pensionAmountFrom;
    private Double pensionAmountTo;
    private Integer workedYearFrom;
    private Integer workedYearTo;
    private Integer page;
    private Integer size;

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

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
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

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public Double getHeightFrom() {
        return heightFrom;
    }

    public void setHeightFrom(Double heightFrom) {
        this.heightFrom = heightFrom;
    }

    public Double getHeightTo() {
        return heightTo;
    }

    public void setHeightTo(Double heightTo) {
        this.heightTo = heightTo;
    }

    public Integer getAgeFrom() {
        return ageFrom;
    }

    public void setAgeFrom(Integer ageFrom) {
        this.ageFrom = ageFrom;
    }

    public Integer getAgeTo() {
        return ageTo;
    }

    public void setAgeTo(Integer ageTo) {
        this.ageTo = ageTo;
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

    public Integer getStudyYearFrom() {
        return studyYearFrom;
    }

    public void setStudyYearFrom(Integer studyYearFrom) {
        this.studyYearFrom = studyYearFrom;
    }

    public Integer getStudyYearTo() {
        return studyYearTo;
    }

    public void setStudyYearTo(Integer studyYearTo) {
        this.studyYearTo = studyYearTo;
    }

    public Double getScholarshipAmountFrom() {
        return scholarshipAmountFrom;
    }

    public void setScholarshipAmountFrom(Double scholarshipAmountFrom) {
        this.scholarshipAmountFrom = scholarshipAmountFrom;
    }

    public Double getScholarshipAmountTo() {
        return scholarshipAmountTo;
    }

    public void setScholarshipAmountTo(Double scholarshipAmountTo) {
        this.scholarshipAmountTo = scholarshipAmountTo;
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

    public Double getPensionAmountFrom() {
        return pensionAmountFrom;
    }

    public void setPensionAmountFrom(Double pensionAmountFrom) {
        this.pensionAmountFrom = pensionAmountFrom;
    }

    public Double getPensionAmountTo() {
        return pensionAmountTo;
    }

    public void setPensionAmountTo(Double pensionAmountTo) {
        this.pensionAmountTo = pensionAmountTo;
    }

    public Integer getWorkedYearFrom() {
        return workedYearFrom;
    }

    public void setWorkedYearFrom(Integer workedYearFrom) {
        this.workedYearFrom = workedYearFrom;
    }

    public Integer getWorkedYearTo() {
        return workedYearTo;
    }

    public void setWorkedYearTo(Integer workedYearTo) {
        this.workedYearTo = workedYearTo;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public Pageable getPageable() {
        return (page != null && size != null) ? PageRequest.of(page, size) : null;
    }
}
