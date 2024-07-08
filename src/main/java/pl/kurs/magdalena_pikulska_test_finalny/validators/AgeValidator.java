package pl.kurs.magdalena_pikulska_test_finalny.validators;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.LocalDate;
import java.time.Period;

public class AgeValidator implements ConstraintValidator<Age, String> {
    @Override
    public void initialize(Age constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {

        String[] peselSplit = s.split("(?!^)");
        LocalDate birthDate = LocalDate.of(getYearOfBirth(peselSplit), getMonthOfBirth(peselSplit), getDayOfBirth(peselSplit));

        LocalDate today = LocalDate.now();
        Period period = Period.between(birthDate, today);
        int age = period.getYears();

        return 18 <= age || age <= 130;
    }

    private static int getDayOfBirth(String[] peselSplit) {
        return Integer.parseInt(peselSplit[4] + peselSplit[5]);
    }

    private static int getMonthOfBirth(String[] peselSplit) {
        int monthInt = Integer.parseInt(peselSplit[2] + peselSplit[3]);
        if (monthInt > 80)
            monthInt = -80;
        if (monthInt > 60)
            monthInt = -60;
        if (monthInt > 40)
            monthInt = -40;
        if (monthInt > 20)
            monthInt = -20;
        return monthInt;
    }

    private static int getYearOfBirth(String[] peselSplit) {
        int monthInt = Integer.parseInt(peselSplit[2] + peselSplit[3]);
        int yearInt = Integer.parseInt(peselSplit[0] + peselSplit[1]);

        if (monthInt > 80)
            yearInt += 1800;
        if (monthInt > 60)
            yearInt += 2200;
        if (monthInt > 40)
            yearInt += 2100;
        if (monthInt > 20)
            yearInt += 2000;
        else
            yearInt += 1900;

        return yearInt;
    }
}
