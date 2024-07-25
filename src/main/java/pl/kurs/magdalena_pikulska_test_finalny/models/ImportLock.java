package pl.kurs.magdalena_pikulska_test_finalny.models;

import jakarta.persistence.*;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
public class ImportLock implements Serializable, Identificationable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private LocalDateTime startDate;
    @Column(unique = true, nullable = false)
    private String lockedByProcess;

    public ImportLock() {
    }

    public ImportLock(LocalDateTime startDate, String lockedByProcess) {
        this.startDate = startDate;
        this.lockedByProcess = lockedByProcess;
    }

    @Override
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public String getLockedByProcess() {
        return lockedByProcess;
    }

    public void setLockedByProcess(String lockedByProcess) {
        this.lockedByProcess = lockedByProcess;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ImportLock that = (ImportLock) o;
        return Objects.equals(id, that.id) && Objects.equals(startDate, that.startDate) && Objects.equals(lockedByProcess, that.lockedByProcess);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, startDate, lockedByProcess);
    }
}
