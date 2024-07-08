package pl.kurs.magdalena_pikulska_test_finalny.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.kurs.magdalena_pikulska_test_finalny.models.Pensioner;

public interface PensionerRepository extends JpaRepository<Pensioner, Long> {
}
