package pl.kurs.magdalena_pikulska_test_finalny.validators;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(
        validatedBy = {PaginationValidator.class}
)
@Target({ElementType.TYPE, ElementType.ANNOTATION_TYPE, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface Pagination {
    String message() default "Field: Page or Size / message: Value must be equal or greater then 0";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
