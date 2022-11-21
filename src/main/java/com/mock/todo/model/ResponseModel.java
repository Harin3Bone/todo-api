package com.mock.todo.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
@Schema(name = "Exception")
public class ResponseModel {

    private int status;
    private String message;
    private List<Object> errorList;
}
