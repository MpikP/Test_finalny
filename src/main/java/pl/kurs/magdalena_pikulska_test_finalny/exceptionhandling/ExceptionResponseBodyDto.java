package pl.kurs.magdalena_pikulska_test_finalny.exceptionhandling;

import java.sql.Timestamp;
import java.util.List;

public class ExceptionResponseBodyDto {

    private List<String> errorsMessages;
    private String errorCode;
    private Timestamp timestamp;

    public ExceptionResponseBodyDto(List<String> errorsMessages, String errorCode, Timestamp timestamp) {
        this.errorsMessages = errorsMessages;
        this.errorCode = errorCode;
        this.timestamp = timestamp;
    }

    public List<String> getErrorsMessages() {
        return errorsMessages;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }
}
