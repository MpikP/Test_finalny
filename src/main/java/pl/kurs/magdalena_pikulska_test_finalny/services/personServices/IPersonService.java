package pl.kurs.magdalena_pikulska_test_finalny.services.personServices;

import pl.kurs.magdalena_pikulska_test_finalny.dto.PersonDto;
import pl.kurs.magdalena_pikulska_test_finalny.models.Identificationable;

public interface IPersonService<T extends Identificationable> {
    T add(T person);
    T getById(Long id);
    T edit(T person);
    PersonDto mapToDto(T person);
}
