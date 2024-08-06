package pl.kurs.magdalena_pikulska_test_finalny.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class WrongEntityStateException extends RuntimeException {
    private final String field;
    private final String rejectedValue;

    public WrongEntityStateException(String message, String field, String rejectedValue) {
        super(message);
        this.field = field;
        this.rejectedValue = rejectedValue;
    }

    public String getField() {
        return field;
    }

    public Object getRejectedValue() {
        return rejectedValue;
    }
}
