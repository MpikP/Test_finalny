package pl.kurs.magdalena_pikulska_test_finalny.commands.create;

import jakarta.validation.constraints.NotBlank;
import pl.kurs.magdalena_pikulska_test_finalny.validators.PersonType;

public class CreatePersonCommand {

    @PersonType
    @NotBlank
    private String type;

    private CreatePersonCommonCommand person;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public CreatePersonCommonCommand getPerson() {
        return person;
    }

    public void setPerson(CreatePersonCommonCommand person) {
        this.person = person;
    }
}
