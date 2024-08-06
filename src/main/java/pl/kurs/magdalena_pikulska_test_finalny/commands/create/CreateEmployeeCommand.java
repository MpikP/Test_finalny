package pl.kurs.magdalena_pikulska_test_finalny.commands.create;

import org.springframework.stereotype.Component;

@Component
@CreatePersonType("employee")
public class CreateEmployeeCommand extends CreatePersonCommonCommand {
}
