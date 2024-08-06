package pl.kurs.magdalena_pikulska_test_finalny.commands.find;

import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@FindPersonType("student")
public class FindStudentAdditionalFieldsCommand extends FindPersonAdditionalFieldsCommand {
    private String graduatedUniversity;
    private Integer studyYear;
    private String studyField;
    private Integer studyYearFrom;
    private Integer studyYearTo;
    private Double scholarshipAmountFrom;
    private Double scholarshipAmountTo;

    @Override
    public void mapParams(Map<String, String> params) {
        if (params.containsKey("graduatedUniversity")) {
            this.graduatedUniversity = params.get("graduatedUniversity");
        }
        if (params.containsKey("studyYear")) {
            this.studyYear = Integer.parseInt(params.get("studyYear"));
        }
        if (params.containsKey("studyField")) {
            this.studyField = params.get("studyField");
        }
        if (params.containsKey("studyYearFrom")) {
            this.studyYearFrom = Integer.parseInt(params.get("studyYearFrom"));
        }
        if (params.containsKey("studyYearTo")) {
            this.studyYearTo = Integer.parseInt(params.get("studyYearTo"));
        }
        if (params.containsKey("scholarshipAmountFrom")) {
            this.scholarshipAmountFrom = Double.parseDouble(params.get("scholarshipAmountFrom"));
        }
        if (params.containsKey("scholarshipAmountTo")) {
            this.scholarshipAmountTo = Double.parseDouble(params.get("scholarshipAmountTo"));
        }
    }

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

    public Integer getStudyYearFrom() {
        return studyYearFrom;
    }

    public void setStudyYearFrom(Integer studyYearFrom) {
        this.studyYearFrom = studyYearFrom;
    }

    public Integer getStudyYearTo() {
        return studyYearTo;
    }

    public void setStudyYearTo(Integer studyYearTo) {
        this.studyYearTo = studyYearTo;
    }

    public Double getScholarshipAmountFrom() {
        return scholarshipAmountFrom;
    }

    public void setScholarshipAmountFrom(Double scholarshipAmountFrom) {
        this.scholarshipAmountFrom = scholarshipAmountFrom;
    }

    public Double getScholarshipAmountTo() {
        return scholarshipAmountTo;
    }

    public void setScholarshipAmountTo(Double scholarshipAmountTo) {
        this.scholarshipAmountTo = scholarshipAmountTo;
    }
}
