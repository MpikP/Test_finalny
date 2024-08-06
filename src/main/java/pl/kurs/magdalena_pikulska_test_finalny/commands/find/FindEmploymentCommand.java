package pl.kurs.magdalena_pikulska_test_finalny.commands.find;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import pl.kurs.magdalena_pikulska_test_finalny.validators.Pagination;

import java.time.LocalDate;

@Pagination
public class FindEmploymentCommand implements IPaginationCommand {
    private Long id;
    private Long idEmployee;
    private LocalDate startDateFrom;
    private LocalDate startDateTo;
    private LocalDate endDateFrom;
    private LocalDate endDateTo;
    private String position;
    private Double salaryFrom;
    private Double salaryTo;
    private Integer page;
    private Integer size;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIdEmployee() {
        return idEmployee;
    }

    public void setIdEmployee(Long idEmployee) {
        this.idEmployee = idEmployee;
    }

    public LocalDate getStartDateFrom() {
        return startDateFrom;
    }

    public void setStartDateFrom(LocalDate startDateFrom) {
        this.startDateFrom = startDateFrom;
    }

    public LocalDate getStartDateTo() {
        return startDateTo;
    }

    public void setStartDateTo(LocalDate startDateTo) {
        this.startDateTo = startDateTo;
    }

    public LocalDate getEndDateFrom() {
        return endDateFrom;
    }

    public void setEndDateFrom(LocalDate endDateFrom) {
        this.endDateFrom = endDateFrom;
    }

    public LocalDate getEndDateTo() {
        return endDateTo;
    }

    public void setEndDateTo(LocalDate endDateTo) {
        this.endDateTo = endDateTo;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public Double getSalaryFrom() {
        return salaryFrom;
    }

    public void setSalaryFrom(Double salaryFrom) {
        this.salaryFrom = salaryFrom;
    }

    public Double getSalaryTo() {
        return salaryTo;
    }

    public void setSalaryTo(Double salaryTo) {
        this.salaryTo = salaryTo;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public Pageable getPageable() {
        return (page != null && size != null) ? PageRequest.of(page, size) : null;
    }
}
