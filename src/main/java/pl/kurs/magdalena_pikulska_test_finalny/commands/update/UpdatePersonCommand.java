package pl.kurs.magdalena_pikulska_test_finalny.commands.update;

import jakarta.validation.constraints.NotBlank;
import pl.kurs.magdalena_pikulska_test_finalny.validators.PersonType;

public class UpdatePersonCommand {
    @PersonType
    @NotBlank
    private String type;
    private UpdatePersonCommonCommand person;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public UpdatePersonCommonCommand getPerson() {
        return person;
    }

    public void setPerson(UpdatePersonCommonCommand person) {
        this.person = person;
    }
}
