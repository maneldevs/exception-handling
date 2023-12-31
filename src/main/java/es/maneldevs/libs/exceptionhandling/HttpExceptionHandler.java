package es.maneldevs.libs.exceptionhandling;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.support.WebExchangeBindException;

@RestControllerAdvice
public class HttpExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public HttpError handleNotFoundException(ServerHttpRequest request, NotFoundException ex) {
        return new HttpError(HttpStatus.NOT_FOUND, request, ex);
    }

    @ExceptionHandler(BadRequestException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public HttpError handleBadRequest(ServerHttpRequest request, BadRequestException ex) {
        return new HttpError(HttpStatus.BAD_REQUEST, request, ex);
    }

    // @ExceptionHandler(MethodArgumentNotValidException.class)
    // @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    // public HttpError handleValidationException(ServerHttpRequest request, MethodArgumentNotValidException ex) {
    //     List<ObjectError> objectErrors = ex.getBindingResult().getAllErrors();
    //     Map<String, String> errors = objectErrors.stream()
    //         .collect(Collectors.toMap(e -> ((FieldError) e).getField(), e -> e.getDefaultMessage()));
    //     return new HttpError(HttpStatus.UNPROCESSABLE_ENTITY, request, ex, errors);
    // }

    @ExceptionHandler(WebExchangeBindException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public HttpError handleWebExchangeBindException(ServerHttpRequest request, WebExchangeBindException ex) {
        List<ObjectError> objectErrors = ex.getBindingResult().getAllErrors();
        Map<String, String> errors = objectErrors.stream()
            .collect(Collectors.toMap(e -> ((FieldError) e).getField(), e -> e.getDefaultMessage()));
        String message = ex.getMessage().substring(0, ex.getMessage().indexOf(' ', ex.getMessage().indexOf(' ')+1));
        return new HttpError(HttpStatus.UNPROCESSABLE_ENTITY, request, message, ex, errors);
    }

    @ExceptionHandler(ValidationException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public HttpError handleValidationException(ServerHttpRequest request, ValidationException ex) {
        return new HttpError(HttpStatus.UNPROCESSABLE_ENTITY, request, ex, ex.getErrors());
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public HttpError handleServerException(ServerHttpRequest request, Exception ex) {
        return new HttpError(HttpStatus.INTERNAL_SERVER_ERROR, request, ex);
    }
    
}
