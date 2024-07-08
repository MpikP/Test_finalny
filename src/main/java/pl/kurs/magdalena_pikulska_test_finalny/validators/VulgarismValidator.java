package pl.kurs.magdalena_pikulska_test_finalny.validators;


import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Value;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class VulgarismValidator implements ConstraintValidator<Vulgarism, String> {

    private List<String> vulgarisms;
    @Value("${vulgarism}")
    private String vulgarismString;


    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {

        if (s == null)
            return true;
        return !vulgarisms.stream().anyMatch(x -> s.trim().toLowerCase().equals(x));
    }

    @Override
    public void initialize(Vulgarism constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
        vulgarisms = Arrays.asList(vulgarismString.split(", "));
        vulgarisms = vulgarisms.stream()
                .map(x -> x.trim().toLowerCase())
                .collect(Collectors.toList());
    }


}
