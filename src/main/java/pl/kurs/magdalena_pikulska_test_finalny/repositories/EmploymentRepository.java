package pl.kurs.magdalena_pikulska_test_finalny.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import pl.kurs.magdalena_pikulska_test_finalny.models.Employee;
import pl.kurs.magdalena_pikulska_test_finalny.models.Employment;

import java.util.List;

public interface EmploymentRepository extends JpaRepository<Employment, Long> {
}
