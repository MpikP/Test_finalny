package pl.kurs.magdalena_pikulska_test_finalny.validators;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import pl.kurs.magdalena_pikulska_test_finalny.commands.FindPersonCommand;
import pl.kurs.magdalena_pikulska_test_finalny.commands.IPaginationCommand;

public class PaginationValidator implements ConstraintValidator<Pagination, IPaginationCommand> {

    @Override
    public void initialize(Pagination constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(IPaginationCommand command, ConstraintValidatorContext constraintValidatorContext) {
        if (command.getSize() == null && command.getPage() == null) {
            return true;
        } else if (command.getSize() == null || command.getPage() == null) {
            return false;
        } else {
            return command.getSize() >= 0 && command.getPage() >= 0;
        }
    }
}
