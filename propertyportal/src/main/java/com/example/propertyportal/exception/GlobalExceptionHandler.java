
package com.example.propertyportal.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // Handle duplicate resource errors
    @ExceptionHandler(DuplicateResourceException.class)
    public ResponseEntity<Map<String, String>> handleDuplicateResource(
            DuplicateResourceException exception) {

        Map<String, String> response = new HashMap<>();
        response.put("error", exception.getMessage());

        return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
    }

    // Handle resource not found errors
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleResourceNotFound(
            ResourceNotFoundException exception) {

        Map<String, String> response = new HashMap<>();
        response.put("error", exception.getMessage());

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    // Handle validation errors
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationErrors(
            MethodArgumentNotValidException exception) {

        Map<String, String> response = new HashMap<>();

        exception.getBindingResult().getFieldErrors().forEach(error ->
                response.put(error.getField(), error.getDefaultMessage())
        );

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    // Handle unexpected errors
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, String>> handleGeneralException(
            Exception exception) {

        //exception.printStackTrace();

        Map<String, String> response = new HashMap<>();
        response.put("error", "Something went wrong. Please try again.");

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }
}