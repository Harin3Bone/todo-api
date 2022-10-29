package com.mock.todo.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;

@Getter
@Setter
@ToString
public class CardModel {

    private String uuid;

    @NotNull(message = "topic is required")
    private String topic;

    @NotNull(message = "content is required")
    private String content;

    private String status;

    @Max(value = 5, message = "priority must in range 0 - 5")
    private int priority;

    private boolean removeStatus;
    private Timestamp createdTimestamp;
    private Timestamp modifiedTimestamp;
    private Timestamp completedTimestamp;

}
