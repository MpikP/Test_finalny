package pl.kurs.magdalena_pikulska_test_finalny.config;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.kurs.magdalena_pikulska_test_finalny.commands.CreatePersonCommandDeserializer;
import pl.kurs.magdalena_pikulska_test_finalny.commands.create.CreatePersonCommand;
import pl.kurs.magdalena_pikulska_test_finalny.commands.UpdatePersonCommandDeserializer;
import pl.kurs.magdalena_pikulska_test_finalny.commands.update.UpdatePersonCommand;
import pl.kurs.magdalena_pikulska_test_finalny.services.PersonTypeRegistry;

@Configuration
public class JacksonConfig {

    @Bean
    public ObjectMapper objectMapper(PersonTypeRegistry personTypeRegistry) {
        ObjectMapper mapper = new ObjectMapper();
        SimpleModule module = new SimpleModule();
        module.addDeserializer(CreatePersonCommand.class, new CreatePersonCommandDeserializer(personTypeRegistry));
        module.addDeserializer(UpdatePersonCommand.class, new UpdatePersonCommandDeserializer(personTypeRegistry));
        mapper.registerModule(module);
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        return mapper;
    }
}
