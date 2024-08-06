package pl.kurs.magdalena_pikulska_test_finalny.exceptionhandling;

import java.sql.Timestamp;
import java.util.List;

public class ExceptionResponseBodySimpleDto {

    private List<ErrorMessageDto> errorsMessages;
    private String errorCode;
    private Timestamp timestamp;

    public ExceptionResponseBodySimpleDto(List<ErrorMessageDto> errorsMessages, String errorCode, Timestamp timestamp) {
        this.errorsMessages = errorsMessages;
        this.errorCode = errorCode;
        this.timestamp = timestamp;
    }

    public List<ErrorMessageDto> getErrorsMessages() {
        return errorsMessages;
    }

    public void setErrorsMessages(List<ErrorMessageDto> errorsMessages) {
        this.errorsMessages = errorsMessages;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }
}
