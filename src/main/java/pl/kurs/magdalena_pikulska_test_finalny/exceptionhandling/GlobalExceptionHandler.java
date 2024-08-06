package pl.kurs.magdalena_pikulska_test_finalny.exceptionhandling;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import pl.kurs.magdalena_pikulska_test_finalny.exceptions.CustomIllegalArgumentException;
import pl.kurs.magdalena_pikulska_test_finalny.exceptions.ResourceNotFoundException;
import pl.kurs.magdalena_pikulska_test_finalny.exceptions.WrongEntityStateException;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
@RestControllerAdvice
public class GlobalExceptionHandler {


    @ExceptionHandler(Exception.class)
    public ResponseEntity<ExceptionResponseBodySimpleDto> handleException(Exception e) {
        ExceptionResponseBodySimpleDto exceptionResponseBodyDto = new ExceptionResponseBodySimpleDto(
                List.of(new ErrorMessageDto("An unexpected error occurred: " + e.getMessage())),
                "BAD_REQUEST",
                Timestamp.from(Instant.now())
        );
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(exceptionResponseBodyDto);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ExceptionResponseBodyDto> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {

        List<FieldErrorDto> errorsMessages = e.getFieldErrors().stream()
                .map(fe -> new FieldErrorDto(fe.getField(), fe.getRejectedValue(), fe.getDefaultMessage()))
                .collect(Collectors.toList());

        if (errorsMessages.isEmpty()) {
            errorsMessages = e.getBindingResult().getAllErrors().stream()
                    .map(x -> new FieldErrorDto(x.getDefaultMessage(), null, null))
                    .collect(Collectors.toList());
        }

        ExceptionResponseBodyDto exceptionResponseBodyDto = new ExceptionResponseBodyDto(
                errorsMessages,
                "BAD_REQUEST",
                Timestamp.from(Instant.now())
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exceptionResponseBodyDto);
    }


    @ExceptionHandler(WrongEntityStateException.class)
    public ResponseEntity<ExceptionResponseBodySimpleDto> handleWrongEntityStateException(WrongEntityStateException e) {
        ExceptionResponseBodySimpleDto exceptionResponseBodyDto = new ExceptionResponseBodySimpleDto(
                List.of(new ErrorMessageDto(e.getMessage())),
                "BAD_REQUEST",
                Timestamp.from(Instant.now())
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exceptionResponseBodyDto);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ExceptionResponseBodySimpleDto> handleResourceNotFoundException(ResourceNotFoundException e) {
        ExceptionResponseBodySimpleDto exceptionResponseBodyDto = new ExceptionResponseBodySimpleDto(
                List.of(new ErrorMessageDto(e.getMessage())),
                "NOT_FOUND",
                Timestamp.from(Instant.now())
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exceptionResponseBodyDto);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ExceptionResponseBodySimpleDto> handleIllegalArgumentException(IllegalArgumentException e) {
        ExceptionResponseBodySimpleDto exceptionResponseBodyDto = new ExceptionResponseBodySimpleDto(
                List.of(new ErrorMessageDto(e.getMessage())),
                "BAD_REQUEST",
                Timestamp.from(Instant.now())
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exceptionResponseBodyDto);
    }


    @ExceptionHandler(CustomIllegalArgumentException.class)
    public ResponseEntity<ExceptionResponseBodyDto> handleCustomIllegalArgumentException(CustomIllegalArgumentException e) {
        ExceptionResponseBodyDto exceptionResponseBodyDto = new ExceptionResponseBodyDto(
                List.of(new FieldErrorDto(e.getField(), e.getRejectedValue(), e.getMessage())),
                "BAD_REQUEST",
                Timestamp.from(Instant.now())
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exceptionResponseBodyDto);
    }

    @ExceptionHandler(SQLException.class)
    public ResponseEntity<ExceptionResponseBodySimpleDto> handleSQLIntegrityConstraintViolationException(SQLException e) {
        ExceptionResponseBodySimpleDto exceptionResponseBodyDto = new ExceptionResponseBodySimpleDto(
                List.of(new ErrorMessageDto(e.getMessage())),
                "BAD_REQUEST",
                Timestamp.from(Instant.now())
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exceptionResponseBodyDto);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ExceptionResponseBodySimpleDto> handleEntityNotFoundExceptionException(EntityNotFoundException e) {
        ExceptionResponseBodySimpleDto exceptionResponseBodyDto = new ExceptionResponseBodySimpleDto(
                List.of(new ErrorMessageDto(e.getMessage())),
                "BAD_REQUEST",
                Timestamp.from(Instant.now())
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exceptionResponseBodyDto);
    }

    @ExceptionHandler(OptimisticLockingFailureException.class)
    public ResponseEntity<ExceptionResponseBodyDto> handleOptimisticLockingFailureException(OptimisticLockingFailureException e) {
        ExceptionResponseBodyDto exceptionResponseBodyDto = new ExceptionResponseBodyDto(
                List.of((e.getMessage() != null) ? new FieldErrorDto("globalError", null, e.getMessage()) :
                        new FieldErrorDto("Optimistic locking failure occurred.", null, null)
                ),
                "CONFLICT",
                Timestamp.from(Instant.now())
        );
        return ResponseEntity.status(HttpStatus.CONFLICT).body(exceptionResponseBodyDto);
    }
}
