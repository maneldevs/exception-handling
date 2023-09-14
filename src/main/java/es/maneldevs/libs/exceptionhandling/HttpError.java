package es.maneldevs.libs.exceptionhandling;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;

public class HttpError {
    private final Instant timestamp;
    private final HttpStatus status;
    private final String message;
    private final String path;
    private final Map<String, String> errors;

    public HttpError(HttpStatus status, String message, String path, Map<String, String> errors) {
        this.timestamp = Instant.now();
        this.status = status;
        this.message = message;
        this.path = path;
        this.errors = errors;
    }

    public HttpError(HttpStatus status, String message, String path) {
        this(status, message, path, new HashMap<>());
    }

    public HttpError(HttpStatus status, ServerHttpRequest request, Exception ex, Map<String, String> errors) {
        this(status, ex.getMessage(), request.getPath().pathWithinApplication().value(), errors);
    }

    public HttpError(HttpStatus status, ServerHttpRequest request, Exception ex) {
        this(status, request, ex, new HashMap<>());
    }

    public Instant getTimestamp() {
        return timestamp;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public String getPath() {
        return path;
    }

    public Map<String, String> getErrors() {
        return errors;
    }

}
