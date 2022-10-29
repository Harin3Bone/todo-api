package com.mock.todo.model;

import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
public class CardModel {

    private String id;
    private String topic;
    private String content;
    private String status;
    private int priority;
    private boolean removeStatus;
    private Timestamp createdTimestamp;
    private Timestamp modifiedTimestamp;
    private Timestamp completedTimestamp;

}
