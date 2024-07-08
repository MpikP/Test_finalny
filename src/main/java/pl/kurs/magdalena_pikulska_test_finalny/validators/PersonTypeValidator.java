package pl.kurs.magdalena_pikulska_test_finalny.validators;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Value;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class PersonTypeValidator implements ConstraintValidator<PersonType, String> {
    private List<String> people;
    @Value("${people}")
    private String peopleString;


    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        if (s == null)
            return true;

        people = Arrays.asList(peopleString.split(", "));
        return people.contains(s.toLowerCase());
    }

    @Override
    public void initialize(PersonType constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

}
