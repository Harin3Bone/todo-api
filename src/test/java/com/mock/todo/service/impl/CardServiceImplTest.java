package com.mock.todo.service.impl;

import com.mock.todo.entity.CardEntity;
import com.mock.todo.enums.CardStatus;
import com.mock.todo.exception.InvalidException;
import com.mock.todo.exception.NotFoundException;
import com.mock.todo.model.CardModel;
import com.mock.todo.repository.CardRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
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
    void listAllCardSystem() {
        cardService.listAllCard();
        verify(cardRepository, times(1)).findAll();
    }

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
        assertEquals(content, cardEntity.getContent());
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
        assertEquals(content, cardEntity.getContent());
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
        Timestamp createdTimestamp = new Timestamp(1667572762);
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        String id = UUID.randomUUID().toString();
        CardModel mockCardModel = new CardModel();
        mockCardModel.setUuid(id);
        mockCardModel.setTopic("Hello World");
        mockCardModel.setContent("Description");
        mockCardModel.setPriority(0);
        mockCardModel.setRemoveStatus(false);
        mockCardModel.setStatus(CardStatus.TODO.toString());
        mockCardModel.setCreatedTimestamp(createdTimestamp);
        mockCardModel.setModifiedTimestamp(createdTimestamp);

        CardEntity mockCardEntity = new CardEntity(mockCardModel);
        when(cardRepository.findById(UUID.fromString(id))).thenReturn(Optional.of(mockCardEntity));

        mockCardModel.setTopic(topicName);
        mockCardModel.setContent(content);
        mockCardModel.setPriority(1);
        mockCardModel.setStatus(CardStatus.IN_PROGRESS.toString());

        CardEntity cardEntity = cardService.updateCardById(id, mockCardModel);
        assertEquals(topicName, cardEntity.getTopic());
        assertEquals(content, cardEntity.getContent());
        assertEquals(CardStatus.IN_PROGRESS, cardEntity.getStatus());
        assertEquals(1, cardEntity.getPriority());
        assertEquals(formatter.format(createdTimestamp), formatter.format(cardEntity.getCreatedTimestamp()));
        assertEquals(formatter.format(new Timestamp(System.currentTimeMillis())), formatter.format(cardEntity.getModifiedTimestamp()));
        assertNull(cardEntity.getCompletedTimestamp());
    }

    @Test
    void testUpdateSuccessWithDoneStatus() {
        String topicName = "Hello World Done";
        String content = "This job is done.";
        Timestamp createdTimestamp = new Timestamp(1667672762);
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        String id = UUID.randomUUID().toString();
        CardModel mockCardModel = new CardModel();
        mockCardModel.setUuid(id);
        mockCardModel.setTopic("Hello World");
        mockCardModel.setContent("Description are in progress");
        mockCardModel.setPriority(0);
        mockCardModel.setRemoveStatus(false);
        mockCardModel.setStatus(CardStatus.IN_PROGRESS.toString());
        mockCardModel.setCreatedTimestamp(createdTimestamp);
        mockCardModel.setModifiedTimestamp(createdTimestamp);

        CardEntity mockCardEntity = new CardEntity(mockCardModel);
        when(cardRepository.findById(UUID.fromString(id))).thenReturn(Optional.of(mockCardEntity));

        mockCardModel.setTopic(topicName);
        mockCardModel.setContent(content);
        mockCardModel.setPriority(1);
        mockCardModel.setStatus(CardStatus.DONE.toString());

        CardEntity cardEntity = cardService.updateCardById(id, mockCardModel);
        assertEquals(topicName, cardEntity.getTopic());
        assertEquals(content, cardEntity.getContent());
        assertEquals(CardStatus.DONE, cardEntity.getStatus());
        assertEquals(1, cardEntity.getPriority());
        assertEquals(formatter.format(createdTimestamp), formatter.format(cardEntity.getCreatedTimestamp()));
        assertEquals(formatter.format(new Timestamp(System.currentTimeMillis())), formatter.format(cardEntity.getModifiedTimestamp()));
        assertEquals(formatter.format(new Timestamp(System.currentTimeMillis())), formatter.format(cardEntity.getCompletedTimestamp()));
    }

    @Test
    void testUpdateNullStatus() {
        String topicName = "Hello World";
        String content = "Description are in progress";
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Timestamp currentTimestamp = new Timestamp(System.currentTimeMillis());
        Timestamp createdTimestamp = new Timestamp(1667672762);

        String id = UUID.randomUUID().toString();
        CardModel mockCardModel = new CardModel();
        mockCardModel.setUuid(id);
        mockCardModel.setTopic(topicName);
        mockCardModel.setContent(content);
        mockCardModel.setPriority(0);
        mockCardModel.setRemoveStatus(false);
        mockCardModel.setStatus(CardStatus.TODO.toString());
        mockCardModel.setCreatedTimestamp(createdTimestamp);
        mockCardModel.setModifiedTimestamp(createdTimestamp);

        CardEntity mockCardEntity = new CardEntity(mockCardModel);
        when(cardRepository.findById(UUID.fromString(id))).thenReturn(Optional.of(mockCardEntity));
        mockCardModel.setStatus(null);
        mockCardModel.setPriority(2);

        CardEntity cardEntity = cardService.updateCardById(id, mockCardModel);
        assertEquals(topicName, cardEntity.getTopic());
        assertEquals(content, cardEntity.getContent());
        assertEquals(CardStatus.TODO, cardEntity.getStatus());
        assertEquals(2, cardEntity.getPriority());
        assertEquals(formatter.format(createdTimestamp), formatter.format(cardEntity.getCreatedTimestamp()));
        assertEquals(formatter.format(currentTimestamp), formatter.format(cardEntity.getModifiedTimestamp()));
    }

    @Test
    void testUpdateInvalidStatus() {
        Timestamp createdTimestamp = new Timestamp(1667672762);

        String id = UUID.randomUUID().toString();
        CardModel mockCardModel = new CardModel();
        mockCardModel.setUuid(id);
        mockCardModel.setTopic("Hello World");
        mockCardModel.setContent("Description are in progress");
        mockCardModel.setPriority(0);
        mockCardModel.setRemoveStatus(false);
        mockCardModel.setStatus(CardStatus.TODO.toString());
        mockCardModel.setCreatedTimestamp(createdTimestamp);
        mockCardModel.setModifiedTimestamp(createdTimestamp);

        CardEntity mockCardEntity = new CardEntity(mockCardModel);
        when(cardRepository.findById(UUID.fromString(id))).thenReturn(Optional.of(mockCardEntity));
        mockCardModel.setStatus("Under Review");
        mockCardModel.setPriority(2);

        assertThatThrownBy(() -> cardService.updateCardById(id, mockCardModel))
                .isInstanceOf(InvalidException.class);
    }

    @ParameterizedTest
    @CsvSource(value = {
            "true",
            "false"
    }, delimiter = ',')
    void testRemoveCardFromBoard(boolean removeStatus) {
        Timestamp currentTimestamp = new Timestamp(System.currentTimeMillis());
        Timestamp createdTimestamp = new Timestamp(1667672762);

        UUID id = UUID.randomUUID();
        CardModel mockCardModel = new CardModel();
        mockCardModel.setUuid(id.toString());
        mockCardModel.setRemoveStatus(false);
        mockCardModel.setStatus(CardStatus.TODO.toString());
        mockCardModel.setCreatedTimestamp(createdTimestamp);
        mockCardModel.setModifiedTimestamp(createdTimestamp);

        CardEntity mockCardEntity = new CardEntity(mockCardModel);
        when(cardRepository.findById(id)).thenReturn(Optional.of(mockCardEntity));
        mockCardModel.setRemoveStatus(removeStatus);
        mockCardEntity.setRemoveStatus(removeStatus);
        mockCardEntity.setModifiedTimestamp(currentTimestamp);

        cardService.updateCardRemoveStatus(id.toString(), removeStatus);
        verify(cardRepository, times(1)).findById(id);
        verify(cardRepository, times(1)).saveAndFlush(mockCardEntity);
    }

    @Test
    void testRemoveCardFromBoardNotFound() {
        UUID id = UUID.randomUUID();
        CardEntity mockCardEntity = new CardEntity();
        when(cardRepository.findById(id)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> cardService.updateCardRemoveStatus(id.toString(), true))
                .isInstanceOf(NotFoundException.class);
        verify(cardRepository, times(1)).findById(id);
        verify(cardRepository, times(0)).saveAndFlush(mockCardEntity);
    }

    @Test
    void testRemoveCardInvalidUUID() {
        String id = "210436d6d4fe4b3cbf4e2d60cc766c10";
        CardEntity mockCardEntity = new CardEntity();

        assertThatThrownBy(() -> cardService.updateCardRemoveStatus(id, true))
                .isInstanceOf(InvalidException.class);
        verify(cardRepository, times(0)).findById(any());
        verify(cardRepository, times(0)).saveAndFlush(mockCardEntity);
    }

    @Test
    void testRemoveCardFromTrashSuccess() {
        UUID id = UUID.randomUUID();
        CardEntity cardEntity = new CardEntity();
        cardEntity.setId(id);
        cardEntity.setRemoveStatus(true);
        when(cardRepository.findById(id)).thenReturn(Optional.of(cardEntity));

        cardService.removeCardFromTrash(id.toString());
        verify(cardRepository, times(1)).findById(any());
        verify(cardRepository, times(1)).deleteById(any());
    }

    @Test
    void testRemoveCardFromTrashNotFound() {
        UUID id = UUID.randomUUID();
        CardEntity cardEntity = new CardEntity();
        cardEntity.setId(id);
        cardEntity.setRemoveStatus(true);
        when(cardRepository.findById(id)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> cardService.removeCardFromTrash(id.toString()))
                .isInstanceOf(NotFoundException.class);
        verify(cardRepository, times(1)).findById(any());
        verify(cardRepository, times(0)).deleteById(any());
    }

    @Test
    void testRemoveCardFromTrashInvalidUUID() {
        String id = "ABC";
        CardEntity cardEntity = new CardEntity();
        cardEntity.setId(UUID.randomUUID());

        assertThatThrownBy(() -> cardService.removeCardFromTrash(id))
                .isInstanceOf(InvalidException.class);
        verify(cardRepository, times(0)).findById(any());
        verify(cardRepository, times(0)).deleteById(any());
    }

    @Test
    void testRemoveCardFromTrashNotInRemoveStatus() {
        UUID id = UUID.randomUUID();
        CardEntity cardEntity = new CardEntity();
        cardEntity.setId(id);
        cardEntity.setRemoveStatus(false);
        when(cardRepository.findById(id)).thenReturn(Optional.of(cardEntity));

        assertThatThrownBy(() -> cardService.removeCardFromTrash(id.toString()))
                .isInstanceOf(InvalidException.class);
        verify(cardRepository, times(1)).findById(any());
        verify(cardRepository, times(0)).deleteById(any());
    }

}
