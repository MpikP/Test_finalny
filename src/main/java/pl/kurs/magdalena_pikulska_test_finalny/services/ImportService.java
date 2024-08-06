package pl.kurs.magdalena_pikulska_test_finalny.services;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.web.multipart.MultipartFile;
import pl.kurs.magdalena_pikulska_test_finalny.models.*;
import pl.kurs.magdalena_pikulska_test_finalny.repositories.ImportStatusRepository;
import pl.kurs.magdalena_pikulska_test_finalny.repositories.PersonRepository;

import org.springframework.transaction.annotation.Transactional;
import pl.kurs.magdalena_pikulska_test_finalny.services.personServices.GenericManagementService;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class ImportService extends GenericManagementService<ImportStatus, ImportStatusRepository> {

    @PersistenceContext
    private EntityManager entityManager;

    private PersonRepository personRepository;
    private ImportStatusRepository importStatusRepository;
    private ImportLockService importLockService;


    public ImportService(ImportStatusRepository repository, PersonRepository personRepository, ImportStatusRepository importStatusRepository, ImportLockService importLockService) {
        super(repository);
        this.personRepository = personRepository;
        this.importStatusRepository = importStatusRepository;
        this.importLockService = importLockService;
    }

    public ImportStatus initializeImportStatus() {
        ImportStatus importStatus = new ImportStatus();
        importStatus.setStatus("IN_PROGRESS");
        importStatus.setCreatedDate(LocalDateTime.now());
        importStatus.setStartDate(LocalDateTime.now());
        importStatus.setProcessedCount(0);
        importStatusRepository.save(importStatus);
        return importStatus;
    }


    private ImportLock doLock() {
        removeExpiredLock();
        try {
            ImportLock importLock = new ImportLock(LocalDateTime.now(), "IMPORT_PROCESS");
            importLockService.add(importLock);
            return importLock;
        } catch (DuplicateKeyException e) {
            return null;
        }
    }


    private void releaseLock(ImportLock importLock) {
        importLockService.delete(importLock);
    }

    private void removeExpiredLock() {
        importLockService.deleteExpiredLock();
    }


    @Async
    @Transactional(rollbackFor = Exception.class)
    public void savePersonFromCsvFile(MultipartFile file, ImportStatus importStatus) throws Exception {
        ImportLock lock = doLock();
        if (lock == null) {
            throw new IllegalArgumentException("Another import is already in progress");
        }

        try {
            processCsvFile(file, importStatus);
            updateImportStatusAsCompleted(importStatus);
        } catch (Exception e) {
            handleImportFailure(importStatus);
            throw new Exception("Failed to save people from CSV file");
        } finally {
            releaseLock(lock);
        }
    }


    public void processCsvFile(final MultipartFile file, ImportStatus importStatus) throws Exception {
        int counter = 0;
        final List<Person> personList = new ArrayList<>();
        try (final BufferedReader br = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
            String line;
            while ((line = br.readLine()) != null) {
                counter++;
                final String[] data = line.split(",");
                final Person person = processRecord(data);
                if (person != null) {
                    personList.add(person);
                }
                if (counter % 5000 == 0) {
                    personRepository.saveAll(personList);
                    entityManager.flush();
                    entityManager.clear();
                    personList.clear();
                    updateImportStatus(importStatus, counter);
                }
            }
            if (!personList.isEmpty()) {
                personRepository.saveAll(personList);
                entityManager.flush();
                entityManager.clear();
            }
        } catch (IOException e) {
            throw new Exception("Failed to parse CSV file", e);
        }
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void handleImportFailure(ImportStatus importStatus) {
        importStatus.setProcessedCount(0);
        importStatus.setStatus("FAILED");
        importStatusRepository.save(importStatus);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void updateImportStatus(ImportStatus importStatus, int processedCount) {
        importStatus.setProcessedCount(processedCount);
        importStatus.setStatus("IN PROGRESS");
        importStatusRepository.save(importStatus);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void updateImportStatusAsCompleted(ImportStatus importStatus) {
        importStatus.setStatus("COMPLETED");
        importStatus.setEndDate(LocalDateTime.now());
        importStatusRepository.save(importStatus);
    }

    public Person processRecord(String[] line) {
        if (line.length < 7) {
            return null;
        }

        String type = line[0];
        String firstName = line[1];
        String lastName = line[2];
        String pesel = line[3];
        double height = Double.parseDouble(line[4]);
        double weight = Double.parseDouble(line[5]);
        String emailAddress = line[6];

        Person person;
        switch (type) {
            case "Employee":
                person = new Employee();
                break;
            case "Student":
                person = new Student();
                if (line.length >= 11) {
                    ((Student) person).setScholarshipAmount(Double.parseDouble(line[7]));
                    ((Student) person).setStudyField(line[8]);
                    ((Student) person).setGraduatedUniversity(line[9]);
                    ((Student) person).setStudyYear(Integer.parseInt(line[10]));
                }
                break;
            case "Pensioner":
                person = new Pensioner();
                if (line.length >= 10) {
                    ((Pensioner) person).setPensionAmount(Double.parseDouble(line[7]));
                    ((Pensioner) person).setWorkedYear(Integer.parseInt(line[8]));
                }
                break;
            default:
                return null;
        }

        person.setFirstName(firstName);
        person.setLastName(lastName);
        person.setPesel(pesel);
        person.setHeight(height);
        person.setWeight(weight);
        person.setEmailAddress(emailAddress);

        return person;
    }
}
