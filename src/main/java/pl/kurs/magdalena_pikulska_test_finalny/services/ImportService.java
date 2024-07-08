package pl.kurs.magdalena_pikulska_test_finalny.services;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import pl.kurs.magdalena_pikulska_test_finalny.models.*;
import pl.kurs.magdalena_pikulska_test_finalny.repositories.ImportStatusRepository;
import pl.kurs.magdalena_pikulska_test_finalny.repositories.PersonRepository;

import org.springframework.transaction.annotation.Transactional;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

@Service
public class ImportService extends GenericManagementService<ImportStatus, ImportStatusRepository> {

    private PersonRepository personRepository;
    private ImportStatusRepository importStatusRepository;
    private final int batchSize = 50000;
    private final Lock importLock = new ReentrantLock();

    public ImportService(ImportStatusRepository repository, PersonRepository personRepository, ImportStatusRepository importStatusRepository) {
        super(repository);
        this.personRepository = personRepository;
        this.importStatusRepository = importStatusRepository;
    }

    @Async
    @Transactional
    public CompletableFuture<Long> savePersonFromCsvFile(MultipartFile file) throws Exception {
        if (!importLock.tryLock()) {
            throw new IllegalArgumentException("Another import is already in progress");
        }

        CompletableFuture<Long> resultFuture = new CompletableFuture<>();
        try {
            ImportStatus importStatus = initializeImportStatus();
            processCsvFile(file, importStatus);
            updateImportStatusAsCompleted(importStatus);
            resultFuture.complete(importStatus.getId());
        } catch (Exception e) {
            throw new Exception("Failed to save persons from CSV file", e);
        } finally {
            importLock.unlock();
        }

        return resultFuture;
    }

    public List<Person> processCsvFile(final MultipartFile file, ImportStatus importStatus) throws Exception {
        int counter = 0;
        final List<Person> personList = new ArrayList<>();
        try {
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
                        personList.clear();
                        updateImportStatus(importStatus, counter);
                    }
                }
                return personList;
            }
        } catch (IOException e) {
            throw new Exception("Failed to parse CSV file", e);
        }
    }

    private void updateImportStatus(ImportStatus importStatus, int processedCount) {
        importStatus.setProcessedCount(processedCount);
        importStatus.setStatus("IN PROGRESS");
        importStatusRepository.save(importStatus);
    }

    private void updateImportStatusAsCompleted(ImportStatus importStatus) {
        importStatus.setStatus("COMPLETED");
        importStatus.setEndDate(LocalDateTime.now());
        importStatusRepository.save(importStatus);
    }

    private ImportStatus initializeImportStatus() {
        ImportStatus importStatus = new ImportStatus();
        importStatus.setStatus("IN_PROGRESS");
        importStatus.setCreatedDate(LocalDateTime.now());
        importStatus.setStartDate(LocalDateTime.now());
        importStatus.setProcessedCount(0);
        importStatusRepository.save(importStatus);
        return importStatus;
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
