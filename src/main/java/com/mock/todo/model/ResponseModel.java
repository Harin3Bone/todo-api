package com.mock.todo.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@Schema(name = "Exception")
public class ResponseModel {

    private int status;
    private String message;
    private List<Object> errorList;
}
