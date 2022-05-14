package com.example.testtask.controllers;

import com.example.testtask.utils.Response;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@ControllerAdvice
public class ExceptionController {
    @ExceptionHandler
    public ResponseEntity<Response> emailConflictException(DataIntegrityViolationException exception) {
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(new Response(exception.getClass().getSimpleName() + ": Client with such email already exists"));
    }

    @ExceptionHandler
    public ResponseEntity<Response> clientNotExistException(NoSuchElementException exception) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new Response(exception.getClass().getSimpleName() + ": Client with such email already exists"));
    }

    @ExceptionHandler
    public ResponseEntity<Response> emptyResultException(EmptyResultDataAccessException exception) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new Response(exception.getClass().getSimpleName() + ": Client with such email already exists"));
    }

    @ExceptionHandler
    public ResponseEntity<Response> notValidDataException(MethodArgumentNotValidException exception) {

        List<String> details = new ArrayList<>();
        for (ObjectError error : exception.getBindingResult().getAllErrors()) {
            details.add(error.getDefaultMessage());
        }
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new Response(exception.getClass().getSimpleName() + ": Not valid\n" + details));
    }
}
