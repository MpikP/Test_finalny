package pl.kurs.magdalena_pikulska_test_finalny.commands;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.kurs.magdalena_pikulska_test_finalny.commands.create.CreatePersonCommand;
import pl.kurs.magdalena_pikulska_test_finalny.commands.create.CreatePersonCommonCommand;
import pl.kurs.magdalena_pikulska_test_finalny.services.PersonTypeRegistry;

import java.io.IOException;

@Component
public class CreatePersonCommandDeserializer extends JsonDeserializer<CreatePersonCommand> {

    private final PersonTypeRegistry personTypeRegistry;

    @Autowired
    public CreatePersonCommandDeserializer(PersonTypeRegistry personTypeRegistry) {
        this.personTypeRegistry = personTypeRegistry;
    }

    @Override
    public CreatePersonCommand deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        ObjectMapper mapper = (ObjectMapper) p.getCodec();
        JsonNode node = mapper.readTree(p);

        String type = node.get("type").asText();
        Class<? extends CreatePersonCommonCommand> commandClass = personTypeRegistry.getCreatePersonCommandClassByType(type);

        CreatePersonCommonCommand personCommand = mapper.treeToValue(node.get("person"), commandClass);

        CreatePersonCommand command = new CreatePersonCommand();
        command.setType(type);
        command.setPerson(personCommand);

        return command;
    }

}
