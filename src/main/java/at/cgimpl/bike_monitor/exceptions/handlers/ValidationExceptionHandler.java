package at.cgimpl.bike_monitor.exceptions.handlers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.*;

@ControllerAdvice
public class ValidationExceptionHandler {

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<String> handleHttpMessageNotReadableException(HttpMessageNotReadableException ex) {
        return new ResponseEntity<>("The request body is not readable, perhaps it's missing?", HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> messages = new HashMap<>();

        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            switch (Objects.requireNonNull(error.getCode())) {
                case "NotNull":
                case "NotEmpty":
                case "NotBlank":
                    messages.put(fieldName, "This field is mandatory");
                    break;
                default:
                    messages.put(fieldName, error.getDefaultMessage());
                    break;
            }
        });

        return new ResponseEntity<>(messages, HttpStatus.BAD_REQUEST);
    }

}
