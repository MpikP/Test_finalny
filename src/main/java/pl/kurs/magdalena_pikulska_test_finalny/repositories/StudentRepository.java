package pl.kurs.magdalena_pikulska_test_finalny.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import pl.kurs.magdalena_pikulska_test_finalny.models.Student;

public interface StudentRepository extends JpaRepository<Student, Long> {
}
