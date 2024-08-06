package pl.kurs.magdalena_pikulska_test_finalny.services.personQuery;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import pl.kurs.magdalena_pikulska_test_finalny.commands.find.FindPersonAdditionalFieldsCommand;
import pl.kurs.magdalena_pikulska_test_finalny.models.Person;

public interface IAdditionalQueryService {
    public Predicate addAdditionalCriteria(CriteriaBuilder criteriaBuilder, Root<Person> root, Predicate predicate, FindPersonAdditionalFieldsCommand command);
}
