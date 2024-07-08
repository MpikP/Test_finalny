package pl.kurs.magdalena_pikulska_test_finalny.validators;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(
        validatedBy = {AgeValidator.class}
)
@Target({ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface Age {
    String message() default "Field: Pesel / message: Age must be between 18 and 130.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
