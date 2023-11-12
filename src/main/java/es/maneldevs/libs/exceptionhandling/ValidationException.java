package es.maneldevs.libs.exceptionhandling;

import java.util.HashMap;
import java.util.Map;

public class ValidationException extends RuntimeException {
    private Map<String, String> errors = new HashMap<>();

    public ValidationException() {
        super();
    }

    public ValidationException(String message) {
        super(message);
    }

    public ValidationException(String message, Throwable cause) {
        super(message, cause);
    }

    public ValidationException(Throwable cause) {
        super(cause);
    }

    public ValidationException(Map<String, String> errors) {
        this();
        this.errors = errors;
    }

    public ValidationException(String message, Map<String, String> errors) {
        this(message);
        this.errors = errors;
    }

    public ValidationException(String message, Throwable cause, Map<String, String> errors) {
        this(message, cause);
        this.errors = errors;
    }

    public ValidationException(Throwable cause, Map<String, String> errors) {
        this(cause);
        this.errors = errors;
    }

    public Map<String, String> getErrors() {
        return errors;
    }

}
