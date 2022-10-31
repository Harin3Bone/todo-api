package com.mock.todo.service.impl;

import com.mock.todo.entity.CardEntity;
import com.mock.todo.enums.CardStatus;
import com.mock.todo.exception.InvalidException;
import com.mock.todo.exception.NotFoundException;
import com.mock.todo.model.CardModel;
import com.mock.todo.repository.CardRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CardServiceImplTest {

    @InjectMocks
    private CardServiceImpl cardService;

    @Mock
    private CardRepository cardRepository;

    @Test
    void listCardOnBoard() {
        cardService.listCardFilterByRemoveStatus(false);
        verify(cardRepository, times(1)).findAllByRemoveStatusIs(false);
    }

    @Test
    void listCardInTrash() {
        cardService.listCardFilterByRemoveStatus(true);
        verify(cardRepository, times(1)).findAllByRemoveStatusIs(true);
    }

    @Test
    void getCardByIdInvalid() {
        assertThatThrownBy(() -> cardService.getCardById("132456"))
                .isInstanceOf(InvalidException.class);
        verify(cardRepository, times(0)).findById(any());
    }

    @Test
    void getCardByIdNotFound() {
        String id = UUID.randomUUID().toString();
        when(cardRepository.findById(any())).thenReturn(Optional.empty());
        assertThatThrownBy(() -> cardService.getCardById(id))
                .isInstanceOf(NotFoundException.class);
        verify(cardRepository, times(1)).findById(any());
    }

    @Test
    void getCardByIdSuccess() {
        String id = UUID.randomUUID().toString();
        CardEntity mockCardEntity = new CardEntity();
        mockCardEntity.setId(UUID.fromString(id));
        when(cardRepository.findById(any())).thenReturn(Optional.of(mockCardEntity));

        CardEntity actualCardEntity = cardService.getCardById(id);
        verify(cardRepository, times(1)).findById(any());
        assertEquals(mockCardEntity, actualCardEntity);
    }

    @Test
    void testInsertCardSuccess() {
        String topicName = "Hello World";
        String content = "Description lorem content.";
        Timestamp currentTimestamp = new Timestamp(System.currentTimeMillis());
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

        CardModel mockCardModel = new CardModel();
        mockCardModel.setUuid(null);
        mockCardModel.setTopic(topicName);
        mockCardModel.setContent(content);
        mockCardModel.setPriority(0);
        mockCardModel.setRemoveStatus(false);
        mockCardModel.setStatus(null);

        CardEntity cardEntity = cardService.insertCardToBoard(mockCardModel);
        assertEquals(topicName, cardEntity.getTopic());
        assertEquals(content,cardEntity.getContent());
        assertEquals(CardStatus.TODO, cardEntity.getStatus());
        assertEquals(0, cardEntity.getPriority());
        assertEquals(formatter.format(currentTimestamp), formatter.format(cardEntity.getCreatedTimestamp()));
        assertEquals(formatter.format(currentTimestamp), formatter.format(cardEntity.getModifiedTimestamp()));
        assertNull(cardEntity.getCompletedTimestamp());
    }

    @Test
    void testInsertCardCompletedData() {
        String topicName = "Hello World";
        String content = "Description lorem content.";
        Timestamp currentTimestamp = new Timestamp(System.currentTimeMillis());
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

        CardModel mockCardModel = new CardModel();
        mockCardModel.setUuid(null);
        mockCardModel.setTopic(topicName);
        mockCardModel.setContent(content);
        mockCardModel.setPriority(2);
        mockCardModel.setRemoveStatus(false);
        mockCardModel.setStatus("IN_PROGRESS");

        CardEntity cardEntity = cardService.insertCardToBoard(mockCardModel);
        assertEquals(topicName, cardEntity.getTopic());
        assertEquals(content,cardEntity.getContent());
        assertEquals(CardStatus.IN_PROGRESS, cardEntity.getStatus());
        assertEquals(2, cardEntity.getPriority());
        assertEquals(formatter.format(currentTimestamp), formatter.format(cardEntity.getCreatedTimestamp()));
        assertEquals(formatter.format(currentTimestamp), formatter.format(cardEntity.getModifiedTimestamp()));
        assertNull(cardEntity.getCompletedTimestamp());
    }

    @Test
    void testInsertCardInvalid() {
        String topicName = "Hello World";
        String content = "Description lorem content.";

        CardModel mockCardModel = new CardModel();
        mockCardModel.setUuid(null);
        mockCardModel.setTopic(topicName);
        mockCardModel.setContent(content);
        mockCardModel.setPriority(0);
        mockCardModel.setRemoveStatus(false);
        mockCardModel.setStatus("invalid");

        assertThatThrownBy(() -> cardService.insertCardToBoard(mockCardModel))
                .isInstanceOf(InvalidException.class);
    }

    @Test
    void testUpdateSuccess() {
        String topicName = "Hello World V2";
        String content = "Description update content.";
        Timestamp currentTimestamp = new Timestamp(System.currentTimeMillis());
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

        CardModel mockCardModel = new CardModel();
        mockCardModel.setUuid(null);
        mockCardModel.setTopic(topicName);
        mockCardModel.setContent(content);
        mockCardModel.setPriority(0);
        mockCardModel.setRemoveStatus(false);
        mockCardModel.setStatus(null);

        CardEntity cardEntity = cardService.insertCardToBoard(mockCardModel);
        assertEquals(topicName, cardEntity.getTopic());
        assertEquals(content,cardEntity.getContent());
        assertEquals(CardStatus.TODO, cardEntity.getStatus());
        assertEquals(0, cardEntity.getPriority());
        assertEquals(formatter.format(currentTimestamp), formatter.format(cardEntity.getCreatedTimestamp()));
        assertEquals(formatter.format(currentTimestamp), formatter.format(cardEntity.getModifiedTimestamp()));
        assertNull(cardEntity.getCompletedTimestamp());
    }

}
