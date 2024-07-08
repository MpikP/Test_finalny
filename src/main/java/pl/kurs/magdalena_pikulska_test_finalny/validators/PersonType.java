package pl.kurs.magdalena_pikulska_test_finalny.validators;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(
        validatedBy = {PersonTypeValidator.class}
)
@Target({ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface PersonType {
    String message() default "Field: Type / message: Invalid person type.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
