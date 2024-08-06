package pl.kurs.magdalena_pikulska_test_finalny.services.personServices;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import pl.kurs.magdalena_pikulska_test_finalny.exceptions.WrongEntityStateException;
import pl.kurs.magdalena_pikulska_test_finalny.models.Identificationable;

@Transactional
public abstract class GenericManagementService<T extends Identificationable, R extends JpaRepository<T, Long> & PagingAndSortingRepository<T, Long>> {
    protected R repository;

    public GenericManagementService(R repository) {
        this.repository = repository;
    }

    public T add(T entity) {
        if (entity.getId() != null)
            throw new WrongEntityStateException("ID", "null", "ID is not null!");
        return repository.save(entity);
    }

    public T getById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Entity with id " + id + " not found"));
    }

    @Transactional
    public T edit(T entity) {
        if (entity.getId() == null) {
            throw new WrongEntityStateException("ID", "null", "Person Id is null!");
        }

        return repository.save(entity);
    }

    public void delete(T entity) {
        if (entity.getId() == null) {
            throw new WrongEntityStateException("ID", "null", "Entity Id is null!");
        }
        repository.delete(entity);

    }

    @Transactional
    protected void flush() {
        repository.flush();
    }
}
