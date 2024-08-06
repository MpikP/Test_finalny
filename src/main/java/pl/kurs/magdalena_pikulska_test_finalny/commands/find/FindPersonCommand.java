package pl.kurs.magdalena_pikulska_test_finalny.commands.find;


import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import pl.kurs.magdalena_pikulska_test_finalny.validators.Pagination;
import pl.kurs.magdalena_pikulska_test_finalny.validators.PersonType;

import java.util.Map;

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
    private Double heightFrom;
    private Double heightTo;
    private Integer ageFrom;
    private Integer ageTo;
    private Integer page;
    private Integer size;
    private FindPersonAdditionalFieldsCommand additionalFields;

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

    public FindPersonAdditionalFieldsCommand getAdditionalFieldsCommand() {
        return additionalFields;
    }

    public void setAdditionalFieldsCommand(FindPersonAdditionalFieldsCommand additionalFieldsCommand) {
        this.additionalFields = additionalFieldsCommand;
    }

    public void mapAdditionalFields(Map<String, String> params) {
        if (additionalFields != null) {
            additionalFields.mapParams(params);
        }
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
