package pl.kurs.magdalena_pikulska_test_finalny.exceptions;

public class CustomIllegalArgumentException extends IllegalArgumentException {
    private final String field;
    private final Object rejectedValue;

    public CustomIllegalArgumentException(String field, Object rejectedValue, String message) {
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
