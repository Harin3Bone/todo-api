package com.mock.todo.entity;

import com.mock.todo.enums.CardStatus;
import com.mock.todo.exception.InvalidException;
import com.mock.todo.model.CardModel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.ColumnTransformer;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.Timestamp;
import java.util.UUID;

import static com.mock.todo.constants.ErrorMessage.INVALID;

@Getter
@Setter
@Entity
@ToString
@Table(name = "card")
@RequiredArgsConstructor
public class CardEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(name = "id")
    @Type(type = "uuid-char")
    private UUID id;

    @Column(name = "topic")
    private String topic;

    @Column(name = "content")
    private String content;

    @Column(name = "status", columnDefinition = "enum('TODO', 'IN_PROGRESS', 'DONE')")
    @ColumnTransformer(read = "UPPER(status)", write = "LOWER(?)")
    @Enumerated(EnumType.STRING)
    private CardStatus status;

    @Column(name = "priority")
    private int priority;

    @Column(name = "remove_status")
    private boolean removeStatus;

    @Column(name = "created_timestamp")
    private Timestamp createdTimestamp;

    @Column(name = "modified_timestamp")
    private Timestamp modifiedTimestamp;

    @Column(name = "completed_timestamp")
    private Timestamp completedTimestamp;

    public CardEntity(CardModel model) {
        try {
            this.id = UUID.fromString(model.getUuid());
            this.status = CardStatus.valueOf(model.getStatus());
        } catch (IllegalArgumentException e) {
            throw new InvalidException(String.format(INVALID,"value of status"));
        }
        this.topic = model.getTopic();
        this.content = model.getContent();
        this.priority = model.getPriority();
        this.removeStatus = model.isRemoveStatus();
        this.createdTimestamp = model.getCreatedTimestamp();
        this.modifiedTimestamp = model.getModifiedTimestamp();
        this.completedTimestamp = model.getCompletedTimestamp();
    }

}
