package pl.kurs.magdalena_pikulska_test_finalny.services.personQuery;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.stereotype.Service;
import pl.kurs.magdalena_pikulska_test_finalny.commands.find.FindPersonAdditionalFieldsCommand;
import pl.kurs.magdalena_pikulska_test_finalny.commands.find.FindStudentAdditionalFieldsCommand;
import pl.kurs.magdalena_pikulska_test_finalny.models.Person;

@Service
@AdditionalQueryFind("student")
public class StudentAdditionalQueryService extends AdditionalQueryService implements IAdditionalQueryService {
    @Override
    public Predicate addAdditionalCriteria(CriteriaBuilder criteriaBuilder, Root<Person> root, Predicate predicate, FindPersonAdditionalFieldsCommand personCommand) {

        FindStudentAdditionalFieldsCommand command = (FindStudentAdditionalFieldsCommand) personCommand;

        if (command.getGraduatedUniversity() != null)
            predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(criteriaBuilder.lower(root.get("graduatedUniversity")), command.getGraduatedUniversity().toLowerCase()));

        if (command.getStudyField() != null)
            predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(criteriaBuilder.lower(root.get("studyField")), command.getStudyField().toLowerCase()));

        if (command.getStudyYearFrom() != null)
            predicate = criteriaBuilder.and(predicate, criteriaBuilder.greaterThanOrEqualTo(root.get("studyYear"), command.getStudyYearFrom()));

        if (command.getStudyYearTo() != null)
            predicate = criteriaBuilder.and(predicate, criteriaBuilder.lessThanOrEqualTo(root.get("studyYear"), command.getStudyYearTo()));

        if (command.getStudyYear() != null)
            predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(
                    root.get("studyYear"), command.getStudyYearTo()));

        if (command.getScholarshipAmountFrom() != null)
            predicate = criteriaBuilder.and(predicate, criteriaBuilder.greaterThanOrEqualTo(root.get("scholarshipAmount"), command.getScholarshipAmountFrom()));

        if (command.getScholarshipAmountTo() != null)
            predicate = criteriaBuilder.and(predicate, criteriaBuilder.lessThanOrEqualTo(root.get("scholarshipAmount"), command.getScholarshipAmountTo()));

        return predicate;
    }
}
