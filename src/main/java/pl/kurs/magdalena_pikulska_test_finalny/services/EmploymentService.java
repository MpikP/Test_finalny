package pl.kurs.magdalena_pikulska_test_finalny.services;

import jakarta.persistence.*;
import jakarta.persistence.criteria.*;
import org.springframework.transaction.annotation.Transactional;
import jakarta.persistence.OptimisticLockException;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import pl.kurs.magdalena_pikulska_test_finalny.commands.FindEmploymentCommand;
import pl.kurs.magdalena_pikulska_test_finalny.models.*;
import pl.kurs.magdalena_pikulska_test_finalny.repositories.EmploymentRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@Service
public class EmploymentService extends GenericManagementService<Employment, EmploymentRepository> {

    @PersistenceContext
    private EntityManager entityManager;

    public EmploymentService(EmploymentRepository repository) {
        super(repository);
    }



    @Transactional
    public Employment update(Employment employment) {
        try {
            Employment existingEmployment = getById(employment.getId());
            if (!Objects.equals(employment.getVersion(), existingEmployment.getVersion())) {
                throw new OptimisticLockingFailureException("Data has been modified by another transaction");
            }
            if (ifDatesAreNotCorrect(employment.getEmployee().getId(), employment.getStartDate())) {
                throw new IllegalArgumentException("Dates overlap with an existing employment.");
            }
            employment.setVersion(existingEmployment.getVersion());

            return edit(employment);
        } catch (OptimisticLockException e) {
            throw new OptimisticLockingFailureException("Data has been modified by another transaction");
        }
    }

    @Transactional
    public Employment addEmployment(Employment employment) {

        if (ifDatesAreNotCorrect(employment.getEmployee().getId(), employment.getStartDate())) {
            throw new IllegalArgumentException("Dates overlap with an existing employment.");
        }

        Employment currentEmployment = getCurrentByEmployeeId(employment.getEmployee().getId());

        if (currentEmployment.getId() != null && currentEmployment.getEndDate() == null) {
            LocalDate newEndDate = employment.getStartDate().minusDays(1);
            currentEmployment.setEndDate(newEndDate);

            Employment existingEmployment = getById(currentEmployment.getId());
            if (!Objects.equals(currentEmployment.getVersion(), existingEmployment.getVersion())) {
                throw new OptimisticLockingFailureException("Data has been modified by another transaction");
            }

            try {
                edit(currentEmployment);
            } catch (OptimisticLockException e) {
                throw new OptimisticLockingFailureException("Data has been modified by another transaction");
            }
        }

        return add(employment);
    }


    @Transactional(readOnly = true)
    public Page<Employment> getEmploymentByCriteria(FindEmploymentCommand command) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Employment> query = criteriaBuilder.createQuery(Employment.class);
        Root<Employment> root = query.from(Employment.class);

        Predicate predicate = criteriaBuilder.conjunction();

        if (command.getId() != null)
            predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get("id"), command.getId()));

        if (command.getIdEmployee() != null)
            predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get("employee").get("id"), command.getIdEmployee()));

        if (command.getPosition() != null)
            predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(criteriaBuilder.lower(root.get("position")), command.getPosition().toLowerCase()));

        if (command.getSalaryFrom() != null)
            predicate = criteriaBuilder.and(predicate, criteriaBuilder.greaterThanOrEqualTo(root.get("salary"), command.getSalaryFrom()));

        if (command.getSalaryTo() != null)
            predicate = criteriaBuilder.and(predicate, criteriaBuilder.lessThanOrEqualTo(root.get("salary"), command.getSalaryTo()));

        if (command.getStartDateFrom() != null) {
            LocalDate fromDateTime = command.getStartDateFrom();
            predicate = criteriaBuilder.and(predicate, criteriaBuilder.greaterThanOrEqualTo(root.get("startDate"), fromDateTime));
        }

        if (command.getStartDateTo() != null) {
            LocalDate toDateTime = command.getStartDateTo();
            predicate = criteriaBuilder.and(predicate, criteriaBuilder.lessThanOrEqualTo(root.get("startDate"), toDateTime));
        }

        if (command.getEndDateFrom() != null) {
            LocalDate fromDateTime = command.getEndDateFrom();
            predicate = criteriaBuilder.and(predicate, criteriaBuilder.greaterThanOrEqualTo(root.get("endDate"), fromDateTime));
        }

        if (command.getEndDateTo() != null) {
            LocalDate toDateTime = command.getEndDateTo();
            predicate = criteriaBuilder.and(predicate, criteriaBuilder.lessThanOrEqualTo(root.get("endDate"), toDateTime));
        }

        query.where(predicate);

        TypedQuery<Employment> typedQuery = entityManager.createQuery(query);

        int pageNumber = 0;
        int pageSize = 10;
        if (command.getPage() != null && command.getSize() != null) {
            pageNumber = command.getPageable().getPageNumber();
            pageSize = command.getPageable().getPageSize();
        }
        typedQuery.setFirstResult(pageNumber * pageSize);
        typedQuery.setMaxResults(pageSize);

        List<Employment> results = typedQuery.getResultList();

        Pageable pageable = PageRequest.of(pageNumber, pageSize);

        int totalResults = results.size();

        Page<Employment> page = new PageImpl<>(results, pageable, totalResults);

        return page;

    }


    private boolean ifDatesAreNotCorrect(Long employeeId, LocalDate startDate) {
        TypedQuery<Employment> query = entityManager.createQuery(
                "SELECT e FROM Employment e WHERE e.employee.id = :employeeId AND " +
                        "(e.endDate IS NULL OR e.endDate <= :startDate) AND " +
                        "(e.startDate IS NULL OR e.startDate <= :startDate)",
                Employment.class
        );
        query.setParameter("employeeId", employeeId);
        query.setParameter("startDate", startDate);

        List<Employment> employments = query.getResultList();

        return !employments.isEmpty();
    }

    public Employment getCurrentByEmployeeId(Long employeeId) {
        TypedQuery<Employment> query = entityManager.createQuery(
                "SELECT e FROM Employment e WHERE e.employee.id = :employeeId AND " +
                        "e.startDate = (SELECT MAX(e2.startDate) FROM Employment e2 WHERE e2.employee.id = e.employee.id)",
                Employment.class
        );

        query.setParameter("employeeId", employeeId);

        try {
            return query.getSingleResult();
        } catch (NoResultException e) {
            return new Employment();
        }
    }

    public Long countEmploymentsByEmployeeId(Long employeeId) {
        TypedQuery<Long> query = entityManager.createQuery(
                "SELECT COUNT(e) AS Qty  FROM Employment e WHERE e.employee.id = :employeeId",
                Long.class
        );
        query.setParameter("employeeId", employeeId);
        Long qty = query.getSingleResult();
        return qty;
    }

//    public Employment getById(Long id) {
//        return getById(id);
//    }
}
