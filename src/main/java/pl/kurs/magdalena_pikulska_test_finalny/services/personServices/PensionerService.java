package pl.kurs.magdalena_pikulska_test_finalny.services.personServices;

import org.springframework.stereotype.Service;
import pl.kurs.magdalena_pikulska_test_finalny.dto.PensionerDto;
import pl.kurs.magdalena_pikulska_test_finalny.dto.PersonDto;
import pl.kurs.magdalena_pikulska_test_finalny.models.Pensioner;
import pl.kurs.magdalena_pikulska_test_finalny.repositories.PensionerRepository;

@Service
@PersonServiceClass("pensioner")
public class PensionerService extends GenericManagementService<Pensioner, PensionerRepository> implements IPersonService<Pensioner> {
    private PersonService personService;

    public PensionerService(PensionerRepository repository, PersonService personService) {
        super(repository);
        this.personService = personService;
    }

    @Override
    public PersonDto mapToDto(Pensioner person) {
        PensionerDto pensionerDto = new PensionerDto();
        pensionerDto.setId(person.getId());
        pensionerDto.setFirstName(person.getFirstName());
        pensionerDto.setLastName(person.getLastName());
        pensionerDto.setPesel(person.getPesel());
        pensionerDto.setHeight(person.getHeight());
        pensionerDto.setWeight(person.getWeight());
        pensionerDto.setEmailAddress(person.getEmailAddress());
        pensionerDto.setSex();
        pensionerDto.setAge();
        pensionerDto.setType("Pensioner");
        pensionerDto.setPensionAmount(person.getPensionAmount());
        pensionerDto.setWorkedYear(person.getWorkedYear());
        return pensionerDto;
    }
}
