package pl.kurs.magdalena_pikulska_test_finalny.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class WrongEntityStateException extends RuntimeException {
    public WrongEntityStateException(String message) {
        super(message);
    }
}
