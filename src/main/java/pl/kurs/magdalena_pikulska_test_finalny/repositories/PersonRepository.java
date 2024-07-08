package pl.kurs.magdalena_pikulska_test_finalny.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.kurs.magdalena_pikulska_test_finalny.models.Person;

public interface PersonRepository extends JpaRepository<Person, Long> {
}
