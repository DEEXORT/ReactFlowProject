package com.javarush.publisher.exception;

import com.javarush.publisher.model.ValidationErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalValidationExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ValidationErrorResponse handleValidationException(MethodArgumentNotValidException ex) {
        Map<String, String> errors = ex.getBindingResult().getAllErrors().stream()
                .filter(FieldError.class::isInstance)
                .map(FieldError.class::cast)
                .collect(Collectors.toMap(FieldError::getField, FieldError::getDefaultMessage));

        return ValidationErrorResponse.builder()
                .errorCode(HttpStatus.BAD_REQUEST.value() + "40")
                .status(HttpStatus.BAD_REQUEST.value())
                .errors(errors)
                .build();
    }

    @ExceptionHandler({
            WriterAlreadyExistsException.class,
            TopicAlreadyExistsException.class,
            MarkerAlreadyExistsException.class})
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ValidationErrorResponse handleAlreadyExistsException(Exception ex) {
        Map<String, String> errors = new HashMap<>();
        errors.put("error", ex.getMessage());

        return ValidationErrorResponse.builder()
                .errorCode(HttpStatus.FORBIDDEN.value() + "40")
                .status(HttpStatus.FORBIDDEN.value())
                .errors(errors)
                .build();
    }

    @ExceptionHandler({
            WriterNotFoundException.class,
            TopicNotFoundException.class,
            MarkerNotFoundException.class,
            ReactionNotFoundException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ValidationErrorResponse handleNotFoundException(Exception ex) {
        Map<String, String> errors = new HashMap<>();
        errors.put("error", ex.getMessage());
        return ValidationErrorResponse.builder()
                .errorCode(HttpStatus.NOT_FOUND.value() + "40")
                .status(HttpStatus.NOT_FOUND.value())
                .errors(errors)
                .build();
    }
}
