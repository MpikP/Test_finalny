package pl.kurs.magdalena_pikulska_test_finalny.services.personQuery;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface AdditionalQueryFind {
    String value();
}
