package pl.kurs.magdalena_pikulska_test_finalny.commands.update;

import org.springframework.stereotype.Component;

@Component
@UpdatePersonType("employee")
public class UpdateEmployeeCommand extends UpdatePersonCommonCommand {
}
