package me.suren.springconcepts.app.controller.advice;

import me.suren.springconcepts.app.dto.validator.UserValidator;
import me.suren.springconcepts.app.exception.CustomAppException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.InitBinder;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@ControllerAdvice
public class GlobalRestControllerAdvice {

    @Autowired
    private UserValidator userValidator;

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.addValidators(userValidator);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleValidationExceptions(
            MethodArgumentNotValidException ex) {

        Map<String, Object> errorResponse = new HashMap<>();
        errorResponse.put("message", ex.getMessage());

        Set<Map> errors = new HashSet<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            FieldError fieldError = (FieldError) error;

            Map<String, String> fieldErrorRecord = new HashMap<>();
            fieldErrorRecord.put("field", fieldError.getField());
            fieldErrorRecord.put("code", fieldError.getCode());
            fieldErrorRecord.put("message", fieldError.getDefaultMessage());

            errors.add(fieldErrorRecord);
        });
        errorResponse.put("errors", errors);

        return ResponseEntity.badRequest()
                .body(errorResponse);
    }

    @ExceptionHandler(CustomAppException.class)
    public ResponseEntity<Map<String, Object>> handleCustomAppExceptions(
            CustomAppException ex) {

        Map<String, Object> errorResponse = new HashMap<>();
        errorResponse.put("message", ex.getMessage());

        return ResponseEntity
                .status(ex.getCode())
                .body(errorResponse);
    }
}
