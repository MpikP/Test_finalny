package pl.kurs.magdalena_pikulska_test_finalny.services;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import pl.kurs.magdalena_pikulska_test_finalny.models.ImportLock;
import pl.kurs.magdalena_pikulska_test_finalny.repositories.ImportLockRepository;
import pl.kurs.magdalena_pikulska_test_finalny.services.personServices.GenericManagementService;

import java.time.LocalDateTime;

@Service
public class ImportLockService extends GenericManagementService<ImportLock, ImportLockRepository> {

    @PersistenceContext
    private EntityManager entityManager;

    @Value("${lockTimeoutMinutes}")
    private long lockTimeoutMinutes;

    public ImportLockService(ImportLockRepository repository) {
        super(repository);
    }

    public void deleteExpiredLock() {
        LocalDateTime timeoutTime = LocalDateTime.now().minusMinutes(lockTimeoutMinutes);
        Query query1 = entityManager.createNativeQuery("DELETE FROM import_lock WHERE start_time < ?");
        query1.setParameter(1, timeoutTime);
        query1.executeUpdate();
    }
}
