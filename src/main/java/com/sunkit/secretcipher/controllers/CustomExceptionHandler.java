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
        return ResponseEntity.badRequest().body(errors.toString());
    }
}
