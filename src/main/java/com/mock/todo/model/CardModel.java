package com.mock.todo.model;

import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;

@Getter
@Setter
@ToString
@Schema(name = "Card Request")
public class CardModel {

    @Hidden
    private String uuid;

    @NotBlank(message = "topic is required and cannot blank")
    private String topic;

    @NotNull(message = "content is required")
    private String content;

    @Schema(defaultValue = "TODO", allowableValues = {"TODO", "IN_PROGRESS", "DONE"})
    private String status;

    @Schema(defaultValue = "0")
    @Min(value = 0, message = "priority must in range 0 - 5")
    @Max(value = 5, message = "priority must in range 0 - 5")
    private int priority;

    @Schema(defaultValue = "false")
    private boolean removeStatus;

    @Hidden
    private Timestamp createdTimestamp;

    @Hidden
    private Timestamp modifiedTimestamp;

    @Hidden
    private Timestamp completedTimestamp;

}
