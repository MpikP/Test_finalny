package pl.kurs.magdalena_pikulska_test_finalny.commands;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import pl.kurs.magdalena_pikulska_test_finalny.commands.update.UpdatePersonCommand;
import pl.kurs.magdalena_pikulska_test_finalny.commands.update.UpdatePersonCommonCommand;
import pl.kurs.magdalena_pikulska_test_finalny.services.PersonTypeRegistry;

import java.io.IOException;

public class UpdatePersonCommandDeserializer extends JsonDeserializer<UpdatePersonCommand> {

    private final PersonTypeRegistry personTypeRegistry;

    @Autowired
    public UpdatePersonCommandDeserializer(PersonTypeRegistry personTypeRegistry) {
        this.personTypeRegistry = personTypeRegistry;
    }

    @Override
    public UpdatePersonCommand deserialize(JsonParser p, DeserializationContext deserializationContext) throws IOException, JacksonException {
        ObjectMapper mapper = (ObjectMapper) p.getCodec();
        JsonNode node = mapper.readTree(p);

        String type = node.get("type").asText();
        Class<? extends UpdatePersonCommonCommand> commandClass = personTypeRegistry.getUpdatePersonCommandClassByType(type);

        UpdatePersonCommonCommand personCommand = mapper.treeToValue(node.get("person"), commandClass);

        UpdatePersonCommand command = new UpdatePersonCommand();
        command.setType(type);
        command.setPerson(personCommand);

        return command;
    }
}
