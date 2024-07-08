package pl.kurs.magdalena_pikulska_test_finalny.models;

import jakarta.persistence.Entity;

@Entity
public class Employee extends Person implements Identificationable {
    private static final long serialVersionUID = 1L;

}
