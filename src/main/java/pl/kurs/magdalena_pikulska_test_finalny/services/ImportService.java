package pl.kurs.magdalena_pikulska_test_finalny.services;

import jakarta.transaction.Transactional;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import pl.kurs.magdalena_pikulska_test_finalny.models.*;
import pl.kurs.magdalena_pikulska_test_finalny.repositories.ImportStatusRepository;
import pl.kurs.magdalena_pikulska_test_finalny.repositories.PersonRepository;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

@Service
public class ImportService extends GenericManagementService<ImportStatus, ImportStatusRepository> {

    private static Lock importLock;
    private PersonRepository personRepository;

    private ImportStatusRepository importStatusRepository;

    public ImportService(ImportStatusRepository repository, PersonRepository personRepository, ImportStatusRepository importStatusRepository) {
        super(repository);
        this.personRepository = personRepository;
        this.importStatusRepository = importStatusRepository;
    }

    private static final int THREAD_COUNT = 10;
    private static final int QUEUE_CAPACITY = 10000;
    private static final int BATCH_SIZE = 5000;
    public String initiateImport(MultipartFile file) {
        if (!importLock.tryLock()) {
            throw new IllegalArgumentException("Other import is in progress.");
        }

        ImportStatus importStatus = newImportStatus();


        try {
            Path tempFile = Files.createTempFile("temp", ".csv");
            Files.copy(file.getInputStream(), tempFile, StandardCopyOption.REPLACE_EXISTING);

            BlockingQueue<CSVRecord> queue = new LinkedBlockingQueue<>(QUEUE_CAPACITY);
            ExecutorService executor = Executors.newFixedThreadPool(THREAD_COUNT);

            try {
                startReaderThread(tempFile, queue);
                startWorkerThreads(queue, executor, importStatus);

                executor.shutdown();
                if (!executor.awaitTermination(1, TimeUnit.SECONDS)) {
                    executor.shutdownNow();
                }

                updateImportStatus(importStatus, "COMPLETED", LocalDateTime.now());
            } catch (Exception e) {
                updateImportStatus(importStatus, "FAILED", e.getMessage());
            } finally {
                Files.deleteIfExists(tempFile);
                importLock.unlock();
            }
        } catch (IOException e) {
            updateImportStatus(importStatus, "FAILED", e.getMessage());
        }

        return importStatus.getId().toString();
    }

    private void startReaderThread(Path tempFile, BlockingQueue<CSVRecord> queue) {
        new Thread(() -> {
            try (BufferedReader reader = Files.newBufferedReader(tempFile)) {
                Iterable<CSVRecord> records = CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(reader);
                for (CSVRecord record : records) {
                    queue.put(record);
                }
                int counter = queue.size() < THREAD_COUNT ? queue.size() : THREAD_COUNT;
                for (int i = 0; i < counter; i++) {
                    queue.put(null);
                }
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }

    private void startWorkerThreads(BlockingQueue<CSVRecord> queue, ExecutorService executor, ImportStatus importStatus) {
        List<Future<?>> futures = new ArrayList<>();

        for (int i = 0; i < THREAD_COUNT; i++) {
            Future<?> future = executor.submit(() -> {
                List<Person> batch = new ArrayList<>(BATCH_SIZE);
                try {
                    while (true) {
                        CSVRecord record = queue.take();
                        if (record == null) {
                            if (!batch.isEmpty()) {
                                saveBatch(batch);
                                updateProcessedCount(importStatus, batch.size());
                                batch.clear();
                            }
                            break;
                        }
                        Person person = processRecord(record);
                        if (person != null) {
                            batch.add(person);
                        }
                        if (batch.size() >= BATCH_SIZE) {
                            saveBatch(batch);
                            updateProcessedCount(importStatus, batch.size());
                            batch.clear();
                        }
                    }
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
            futures.add(future);
        }

        try {
            for (Future<?> future : futures) {
                future.get(); // Wait for threads to complete
            }
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace(); // Log or handle exceptions
        }
    }


    @Transactional
    public Person processRecord(CSVRecord record) {
        String type = record.get("type");
        String firstName = record.get("firstName");
        String lastName = record.get("lastName");
        String pesel = record.get("pesel");
        double height = Double.parseDouble(record.get("height"));
        double weight = Double.parseDouble(record.get("weight"));
        String emailAddress = record.get("emailAddress");

        Person person;
        switch (type) {
            case "Employee":
                person = new Employee();
                break;
            case "Student":
                person = new Student();
                ((Student) person).setScholarshipAmount(Double.parseDouble(record.get("scholarshipAmount")));
                ((Student) person).setStudyField(record.get("studyField"));
                ((Student) person).setGraduatedUniversity(record.get("graduatedUniversity"));
                ((Student) person).setStudyYear(Integer.parseInt(record.get("studyYear")));
                break;
            case "Pensioner":
                person = new Pensioner();
                ((Pensioner) person).setPensionAmount(Double.parseDouble(record.get("pensionAmount")));
                ((Pensioner) person).setWorkedYear(Integer.parseInt(record.get("workedYear")));
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

    @Transactional
    public void saveBatch(List<Person> batch) {
        personRepository.saveAll(batch);
    }

    @Transactional
    private ImportStatus newImportStatus(){
        ImportStatus importStatus = new ImportStatus();
        importStatus.setStatus("STARTED");
        importStatus.setCreatedDate(LocalDateTime.now());
        importStatus = importStatusRepository.save(importStatus);
        return importStatus;
    }
    @Transactional
    private void updateProcessedCount(ImportStatus importStatus, int count) {
        importStatus.incrementProcessedCount(count);
        importStatus.setStatus("IN PROGRESS");
        importStatusRepository.save(importStatus);
    }

    @Transactional
    private void updateImportStatus(ImportStatus importStatus, String status, LocalDateTime endDate) {
        importStatus.setStatus(status);
        importStatus.setEndDate(endDate);
        importStatusRepository.save(importStatus);
    }

    @Transactional
    private void updateImportStatus(ImportStatus importStatus, String status, String errorMessage) {
        importStatus.setStatus(status);
        importStatus.setErrorMessage(errorMessage);
        importStatusRepository.save(importStatus);
    }


    public ImportStatus getStatus(Long id) {
        return getById(id);
    }

}
