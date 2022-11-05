package com.mock.todo.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class ResponseModel {

    private int status;
    private String message;
    private List<Object> errorList;
}
