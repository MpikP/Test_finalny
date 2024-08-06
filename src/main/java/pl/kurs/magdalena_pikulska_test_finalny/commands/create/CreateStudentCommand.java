package pl.kurs.magdalena_pikulska_test_finalny.commands.create;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Positive;
import org.springframework.stereotype.Component;
import pl.kurs.magdalena_pikulska_test_finalny.commands.IPersonCommand;
import pl.kurs.magdalena_pikulska_test_finalny.validators.Vulgarism;
@Component
@CreatePersonType("student")
public class CreateStudentCommand extends CreatePersonCommonCommand implements IPersonCommand {
    @Vulgarism
    private String graduatedUniversity;
    @Positive
    @Max(10)
    private Integer studyYear;
    @Vulgarism
    private String studyField;
    @Positive
    private Double scholarshipAmount;

    public String getGraduatedUniversity() {
        return graduatedUniversity;
    }

    public void setGraduatedUniversity(String graduatedUniversity) {
        this.graduatedUniversity = graduatedUniversity;
    }

    public Integer getStudyYear() {
        return studyYear;
    }

    public void setStudyYear(Integer studyYear) {
        this.studyYear = studyYear;
    }

    public String getStudyField() {
        return studyField;
    }

    public void setStudyField(String studyField) {
        this.studyField = studyField;
    }

    public Double getScholarshipAmount() {
        return scholarshipAmount;
    }

    public void setScholarshipAmount(Double scholarshipAmount) {
        this.scholarshipAmount = scholarshipAmount;
    }
}
