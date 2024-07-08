package pl.kurs.magdalena_pikulska_test_finalny.services;

import org.springframework.stereotype.Service;
import pl.kurs.magdalena_pikulska_test_finalny.models.Pensioner;
import pl.kurs.magdalena_pikulska_test_finalny.repositories.PensionerRepository;

@Service
public class PensionerService extends GenericManagementService<Pensioner, PensionerRepository> implements IPersonService<Pensioner> {
    public PensionerService(PensionerRepository repository) {
        super(repository);
    }
}
