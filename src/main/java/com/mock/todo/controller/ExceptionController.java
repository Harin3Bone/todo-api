package com.mock.todo.controller;

import com.mock.todo.exception.InvalidException;
import com.mock.todo.exception.NotFoundException;
import com.mock.todo.model.ResponseModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.mock.todo.constants.ErrorMessage.INVALID;
import static com.mock.todo.constants.ErrorMessage.UNEXPECTED_EXCEPTION;

@Slf4j
@RestControllerAdvice
public class ExceptionController {

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseModel unexpectedException(Exception e) {
        log.error("UnexpectedException class: {}", e.getClass());
        log.error("UnexpectedException message: {}", e.getMessage());
        return new ResponseModel(HttpStatus.INTERNAL_SERVER_ERROR.value(), UNEXPECTED_EXCEPTION, new ArrayList<>());
    }

    @ExceptionHandler(value = {InvalidException.class, NotFoundException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseModel unauthorized(RuntimeException e) {
        return new ResponseModel(HttpStatus.BAD_REQUEST.value(), e.getMessage(), new ArrayList<>());
    }

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    public ResponseModel handleMissingValidateBody(MethodArgumentNotValidException e) {
        List<Object> errList = new ArrayList<>();
        for (FieldError err : e.getFieldErrors()) {
            Map<String, Object> errObj = new HashMap<>();
            errObj.put("field", err.getField());
            errObj.put("cause", err.getDefaultMessage());
            errList.add(errObj);
        }
        return new ResponseModel(HttpStatus.BAD_REQUEST.value(), String.format(INVALID, "data"), errList);
    }
}
