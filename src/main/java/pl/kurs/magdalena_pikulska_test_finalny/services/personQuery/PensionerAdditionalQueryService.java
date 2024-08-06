package pl.kurs.magdalena_pikulska_test_finalny.services.personQuery;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.stereotype.Service;
import pl.kurs.magdalena_pikulska_test_finalny.commands.find.FindPensionerAdditionalFieldsCommand;
import pl.kurs.magdalena_pikulska_test_finalny.commands.find.FindPersonAdditionalFieldsCommand;
import pl.kurs.magdalena_pikulska_test_finalny.models.Person;

@Service
@AdditionalQueryFind("pensioner")
public class PensionerAdditionalQueryService extends AdditionalQueryService implements IAdditionalQueryService {
    @Override
    public Predicate addAdditionalCriteria(CriteriaBuilder criteriaBuilder, Root<Person> root, Predicate predicate, FindPersonAdditionalFieldsCommand personCommand) {
        FindPensionerAdditionalFieldsCommand command = (FindPensionerAdditionalFieldsCommand) personCommand;
        if (command.getPensionAmountFrom() != null)
            predicate = criteriaBuilder.and(predicate, criteriaBuilder.greaterThanOrEqualTo(root.get("pensionAmount"), command.getPensionAmountFrom()));

        if (command.getPensionAmountTo() != null)
            predicate = criteriaBuilder.and(predicate, criteriaBuilder.lessThanOrEqualTo(root.get("pensionAmount"), command.getPensionAmountTo()));

        if (command.getWorkedYearFrom() != null)
            predicate = criteriaBuilder.and(predicate, criteriaBuilder.greaterThanOrEqualTo(root.get("workedYear"), command.getWorkedYearFrom()));

        if (command.getWorkedYearTo() != null)
            predicate = criteriaBuilder.and(predicate, criteriaBuilder.lessThanOrEqualTo(root.get("workedYear"), command.getWorkedYearTo()));

        return predicate;
    }
}
