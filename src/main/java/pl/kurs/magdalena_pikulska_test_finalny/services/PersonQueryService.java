package pl.kurs.magdalena_pikulska_test_finalny.services;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import pl.kurs.magdalena_pikulska_test_finalny.commands.FindPersonCommand;
import pl.kurs.magdalena_pikulska_test_finalny.models.*;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

@Component
public class PersonQueryService {

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private PersonService personService;

    @Transactional(readOnly = true)
    public Page<Person> getPersonByCriteria(FindPersonCommand command) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Person> query = criteriaBuilder.createQuery(Person.class);
        Root<Person> root = query.from(Person.class);

        Predicate predicate = criteriaBuilder.conjunction();

        if (command.getType() != null) {
            predicate = criteriaBuilder.and(predicate, personService.getPersonTypePredicate(criteriaBuilder, root, command.getType()));
        }

        if (command.getId() != null)
            predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get("id"), command.getId()));

        if (command.getFirstName() != null)
            predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(criteriaBuilder.lower(root.get("firsName")), command.getFirstName().toLowerCase()));

        if (command.getLastName() != null)
            predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(criteriaBuilder.lower(root.get("lastName")), command.getLastName().toLowerCase()));

        if (command.getPesel() != null)
            predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get("pesel"), command.getPesel()));

        if (command.getEmailAddress() != null)
            predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(criteriaBuilder.lower(root.get("emailAddress")), command.getEmailAddress().toLowerCase()));

        if (command.getSex() != null) {
            Expression<Integer> sexDigit = criteriaBuilder.substring(root.get("pesel"), 9, 1).as(Integer.class);
            if (command.getSex().equalsIgnoreCase("woman")) {
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(criteriaBuilder.mod(sexDigit, 2), 1));
            } else {
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(criteriaBuilder.mod(sexDigit, 2), 0));
            }
        }

        if (command.getHeightFrom() != null)
            predicate = criteriaBuilder.and(predicate, criteriaBuilder.greaterThanOrEqualTo(root.get("height"), command.getHeightFrom()));

        if (command.getHeightTo() != null)
            predicate = criteriaBuilder.and(predicate, criteriaBuilder.lessThanOrEqualTo(root.get("height"), command.getHeightTo()));


        if (command.getAgeFrom() != null || command.getAgeTo() != null) {
            predicate = addAgePredicates(criteriaBuilder, root, command.getAgeFrom(), command.getAgeTo(), predicate);
        }


//        if (command.getType() != null && command.getType().equalsIgnoreCase("employee")) {
//            Join<Person, Employee> employeeJoin = root.join("employee", JoinType.INNER);
//            Join<Employee, Employment> employmentJoin = employeeJoin.join("employment", JoinType.INNER);
//
//
//
//            Subquery<LocalDate> subquery = query.subquery(LocalDate.class);
//            Root<Employment> subRoot = subquery.from(Employment.class);
//            subquery.select(criteriaBuilder.greatest(subRoot.<LocalDate>get("startDate")))
//                    .where(criteriaBuilder.equal(subRoot.get("employee"), employmentJoin));
//
//            predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(employmentJoin.get("startDate"), subquery));
//
//            if (command.getEmploymentStartDateFrom() != null) {
//                LocalDate fromDateTime = command.getEmploymentStartDateFrom();
//                predicate = criteriaBuilder.and(predicate, criteriaBuilder.greaterThanOrEqualTo(employmentJoin.get("startDate"), fromDateTime));
//            }
//
//            if (command.getEmploymentStartDateTo() != null) {
//                LocalDate toDateTime = command.getEmploymentStartDateTo();
//                predicate = criteriaBuilder.and(predicate, criteriaBuilder.lessThanOrEqualTo(employmentJoin.get("startDate"), toDateTime));
//            }
//
//            if (command.getPosition() != null) {
//                predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(criteriaBuilder.lower(employmentJoin.get("position")), command.getPosition().toLowerCase()));
//            }
//
//            if (command.getSalaryFrom() != null) {
//                predicate = criteriaBuilder.and(predicate, criteriaBuilder.greaterThanOrEqualTo(employmentJoin.get("salary"), command.getSalaryFrom()));
//            }
//
//            if (command.getSalaryTo() != null) {
//                predicate = criteriaBuilder.and(predicate, criteriaBuilder.lessThanOrEqualTo(employmentJoin.get("salary"), command.getSalaryTo()));
//            }
//        }


        if (command.getGraduatedUniversity() != null)
            predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(criteriaBuilder.lower(root.get("graduatedUniversity")), command.getGraduatedUniversity().toLowerCase()));

        if (command.getStudyField() != null)
            predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(criteriaBuilder.lower(root.get("studyField")), command.getStudyField().toLowerCase()));

        if (command.getStudyYearFrom() != null)
            predicate = criteriaBuilder.and(predicate, criteriaBuilder.greaterThanOrEqualTo(root.get("studyYear"), command.getStudyYearFrom()));

        if (command.getStudyYearTo() != null)
            predicate = criteriaBuilder.and(predicate, criteriaBuilder.lessThanOrEqualTo(root.get("studyYear"), command.getStudyYearTo()));

        if (command.getStudyYear() != null)
            predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get("studyYear"), command.getStudyYearTo()));

        if (command.getScholarshipAmountFrom() != null)
            predicate = criteriaBuilder.and(predicate, criteriaBuilder.greaterThanOrEqualTo(root.get("scholarshipAmount"), command.getScholarshipAmountFrom()));

        if (command.getScholarshipAmountTo() != null)
            predicate = criteriaBuilder.and(predicate, criteriaBuilder.lessThanOrEqualTo(root.get("scholarshipAmount"), command.getScholarshipAmountTo()));

        if (command.getPensionAmountFrom() != null)
            predicate = criteriaBuilder.and(predicate, criteriaBuilder.greaterThanOrEqualTo(root.get("pensionAmount"), command.getPensionAmountFrom()));

        if (command.getPensionAmountTo() != null)
            predicate = criteriaBuilder.and(predicate, criteriaBuilder.lessThanOrEqualTo(root.get("pensionAmount"), command.getPensionAmountTo()));

        if (command.getWorkedYearFrom() != null)
            predicate = criteriaBuilder.and(predicate, criteriaBuilder.greaterThanOrEqualTo(root.get("workedYear"), command.getWorkedYearFrom()));

        if (command.getWorkedYearTo() != null)
            predicate = criteriaBuilder.and(predicate, criteriaBuilder.lessThanOrEqualTo(root.get("workedYear"), command.getWorkedYearTo()));

        query.where(predicate);

        TypedQuery<Person> typedQuery = entityManager.createQuery(query);

        int pageNumber = 0;
        int pageSize = 10;
        if (command.getPage() != null && command.getSize() != null) {
            pageNumber = command.getPageable().getPageNumber();
            pageSize = command.getPageable().getPageSize();
        }
        typedQuery.setFirstResult(pageNumber * pageSize);
        typedQuery.setMaxResults(pageSize);

        List<Person> results = typedQuery.getResultList();

        Pageable pageable = PageRequest.of(pageNumber, pageSize);

        int totalResults = results.size();

        Page<Person> page = new PageImpl<>(results, pageable, totalResults);
        return page;

    }

    private Predicate addAgePredicates(CriteriaBuilder criteriaBuilder, Root<Person> root, Integer ageFrom, Integer ageTo, Predicate predicate) {
        Expression<String> peselExpression = root.get("pesel");

        Expression<Integer> birthYear = getYearOfBirth(criteriaBuilder, peselExpression);
        Expression<Integer> birthMonth = criteriaBuilder.substring(peselExpression, 3, 2).as(Integer.class);
        Expression<Integer> birthDay = criteriaBuilder.substring(peselExpression, 5, 2).as(Integer.class);

        LocalDate now = LocalDate.now();

        if (ageFrom != null) {
            LocalDate fromDate = now.minusYears(ageFrom);
            Predicate yearPredicate = criteriaBuilder.lessThanOrEqualTo(birthYear, fromDate.getYear());
            Predicate dayPredicate = criteriaBuilder.lessThanOrEqualTo(birthDay, fromDate.getDayOfMonth());

            Predicate fromPredicate = criteriaBuilder.and(
                    yearPredicate,
                    criteriaBuilder.or(
                            criteriaBuilder.lessThan(birthYear, fromDate.getYear()),
                            criteriaBuilder.and(
                                    criteriaBuilder.equal(birthYear, fromDate.getYear()),
                                    criteriaBuilder.or(
                                            criteriaBuilder.lessThan(birthMonth, fromDate.getMonthValue()),
                                            criteriaBuilder.and(
                                                    criteriaBuilder.equal(birthMonth, fromDate.getMonthValue()),
                                                    dayPredicate
                                            )
                                    )
                            )
                    )
            );
            predicate = criteriaBuilder.and(predicate, fromPredicate);
        }

        if (ageTo != null) {
            LocalDate toDate = now.minusYears(ageTo);
            Predicate yearPredicate = criteriaBuilder.greaterThanOrEqualTo(birthYear, toDate.getYear());
            Predicate dayPredicate = criteriaBuilder.greaterThanOrEqualTo(birthDay, toDate.getDayOfMonth());

            Predicate toPredicate = criteriaBuilder.and(
                    yearPredicate,
                    criteriaBuilder.or(
                            criteriaBuilder.greaterThan(birthYear, toDate.getYear()),
                            criteriaBuilder.and(
                                    criteriaBuilder.equal(birthYear, toDate.getYear()),
                                    criteriaBuilder.or(
                                            criteriaBuilder.greaterThan(birthMonth, toDate.getMonthValue()),
                                            criteriaBuilder.and(
                                                    criteriaBuilder.equal(birthMonth, toDate.getMonthValue()),
                                                    dayPredicate
                                            )
                                    )
                            )
                    )
            );
            predicate = criteriaBuilder.and(predicate, toPredicate);
        }

        return predicate;
    }

    private Expression<Integer> getYearOfBirth(CriteriaBuilder criteriaBuilder, Expression<String> peselExpression) {
        Expression<Integer> yearInt = criteriaBuilder.substring(peselExpression, 1, 2).as(Integer.class);
        Expression<Integer> monthInt = criteriaBuilder.substring(peselExpression, 3, 2).as(Integer.class);

        Expression<Integer> yearAdjusted = criteriaBuilder.<Integer>selectCase()
                .when(criteriaBuilder.greaterThan(monthInt, 80), criteriaBuilder.sum(yearInt, 1800))
                .when(criteriaBuilder.greaterThan(monthInt, 60), criteriaBuilder.sum(yearInt, 2200))
                .when(criteriaBuilder.greaterThan(monthInt, 40), criteriaBuilder.sum(yearInt, 2100))
                .when(criteriaBuilder.greaterThan(monthInt, 20), criteriaBuilder.sum(yearInt, 2000))
                .otherwise(criteriaBuilder.sum(yearInt, 1900));


        return yearAdjusted;
    }


}
