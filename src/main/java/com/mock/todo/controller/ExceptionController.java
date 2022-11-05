package com.mock.todo.controller;

import com.mock.todo.exception.InvalidException;
import com.mock.todo.exception.NotFoundException;
import com.mock.todo.model.ResponseModel;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;

@RestControllerAdvice
public class ExceptionController {

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseModel unexpectedException(Exception e) {
        return new ResponseModel(HttpStatus.INTERNAL_SERVER_ERROR.value(),e.getMessage(), new ArrayList<>());
    }

    @ExceptionHandler(value = { InvalidException.class, NotFoundException.class })
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseModel unauthorized(RuntimeException e) {
        return new ResponseModel(HttpStatus.BAD_REQUEST.value(),e.getMessage(), new ArrayList<>());
    }
}
