package com.sunkit.secretcipher.controllers;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler({ConstraintViolationException.class})
    public ResponseEntity<?> handleConstraintViolationExceptions(
            ConstraintViolationException e,
            WebRequest request) {
        List<String> errors = new ArrayList<>();
        for (ConstraintViolation<?> violation: e.getConstraintViolations()) {
            errors.add(violation.getMessage());
        }
        StringBuilder errorMessageBuilder = new StringBuilder();
        for (int i = 0; i < errors.size(); i++) {
            errorMessageBuilder.append(
                    String.format("%s. %s\n", i + 1, errors.get(i)));
        }

        return ResponseEntity.badRequest().body(errorMessageBuilder.toString());
    }
}
