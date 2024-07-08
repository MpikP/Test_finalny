package pl.kurs.magdalena_pikulska_test_finalny.validators;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(
        validatedBy = {VulgarismValidator.class}
)
@Target({ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface Vulgarism {

    String message() default "You can't use vulgarism!";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}

