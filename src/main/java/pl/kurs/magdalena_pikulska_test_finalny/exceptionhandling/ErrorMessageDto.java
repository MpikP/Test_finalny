package pl.kurs.magdalena_pikulska_test_finalny.exceptionhandling;

public class ErrorMessageDto {
    private String message;

    public ErrorMessageDto(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
