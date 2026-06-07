package com.exam.resumeBuilder.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    //the @RestControllerAdvice annotation is handle all the controllers exceptions in globally under the one class

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleValidationExceptions(MethodArgumentNotValidException ex){
        log.info("Inside GlobalExceptionHandler - handleValidationExceptions()");
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach(error ->{
            String fieldName = ((FieldError)error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        Map<String, Object> response = new HashMap<>();
        response.put("message: ", "Validation is failed");
        response.put("errors: ", errors);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }
        @ExceptionHandler(ResourceExistException.class)
    public ResponseEntity<Map<String, Object>> handleResourceExitsException(ResourceExistException resourceExistException){
            log.info("Inside GlobalExceptionHandler - handleResourceExitsException()");
            Map<String, Object> response = new HashMap<>();
        response.put("message", "Resource exists");
        response.put("errors", resourceExistException.getMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
        }
        @ExceptionHandler(Exception.class)
    public ResponseEntity <Map<String, Object>> HandleGenericException(Exception e){
            log.info("Inside GlobalExceptionHandler - HandleGenericException");
            Map<String, Object> response = new HashMap<>();
            response.put("message", "Something went wrong. Contact administrator");
            response.put("errors", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
}
