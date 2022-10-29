package com.mock.todo.service.impl;

import com.mock.todo.entity.CardEntity;
import com.mock.todo.enums.CardStatus;
import com.mock.todo.exception.InvalidException;
import com.mock.todo.exception.NotFoundException;
import com.mock.todo.model.CardModel;
import com.mock.todo.repository.CardRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

import static com.mock.todo.constants.ErrorMessage.INVALID;
import static com.mock.todo.constants.ErrorMessage.NOT_FOUND;

@Slf4j
@Service
@RequiredArgsConstructor
public class CardServiceImpl {

    private final CardRepository cardRepository;

    public List<CardEntity> listCardFilterByRemoveStatus(boolean isRemove) {
        log.info("listCardFilterByRemoveStatus: {}", isRemove);
        return cardRepository.findAllByRemoveStatusIs(isRemove);
    }

    public CardEntity getCardById(String id) {
        log.info("getCardById begin, id: {}", id);
        try {
            return cardRepository.findById(UUID.fromString(id)).orElseThrow(
                    () -> new NotFoundException(String.format(NOT_FOUND, id))
            );
        } catch (IllegalArgumentException e) {
            log.error("getCardById message: {}", e.getMessage());
            throw new InvalidException(String.format(INVALID,"card id."));
        }
    }

    @Transactional
    public CardEntity insertCardToBoard(CardModel cardModel) {
        log.info("insertCardToBoard begin.");
        log.debug("insertCardToBoard model: {}", cardModel);
        Timestamp currentTimestamp = new Timestamp(System.currentTimeMillis());
        cardModel.setUuid(UUID.randomUUID().toString());
        cardModel.setCreatedTimestamp(currentTimestamp);
        cardModel.setModifiedTimestamp(currentTimestamp);

        if (cardModel.getStatus() == null) {
            cardModel.setStatus(CardStatus.TODO.toString());
        }

        CardEntity cardEntity = new CardEntity(cardModel);
        log.info("insertCardToBoard id: {}", cardEntity.getId());
        log.debug("insertCardToBoard entity: {}", cardEntity);
        cardRepository.saveAndFlush(cardEntity);
        return cardEntity;
    }

    @Transactional
    public CardEntity updateCardById(String id, CardModel cardModel) {
        log.info("updateCardById begin.");
        log.debug("updateCardById model: {}", cardModel);

        Timestamp currentTimestamp = new Timestamp(System.currentTimeMillis());
        CardEntity cardEntity = getCardById(id);
        cardEntity.setTopic(cardModel.getTopic());
        cardEntity.setContent(cardModel.getTopic());
        cardEntity.setPriority(cardModel.getPriority());
        cardEntity.setModifiedTimestamp(currentTimestamp);
        try {
            if (cardModel.getStatus() != null) {
                CardStatus status = CardStatus.valueOf(cardModel.getStatus());
                if (status.equals(CardStatus.DONE)) {
                    cardEntity.setCompletedTimestamp(currentTimestamp);
                }
                cardEntity.setStatus(status);
            }
        } catch (IllegalArgumentException e) {
            throw new InvalidException(String.format(INVALID,"value of status"));
        }

        log.info("updateCardById id: {}", cardEntity.getId());
        log.debug("updateCardById entity: {}", cardEntity);
        cardRepository.saveAndFlush(cardEntity);
        return cardEntity;
    }

    @Transactional
    public void updateCardRemoveStatus(CardEntity cardEntity, boolean removeStatus) {
        log.info("updateCardRemoveStatus begin, id: {}", cardEntity.getId());
        cardEntity.setRemoveStatus(removeStatus);
        cardRepository.saveAndFlush(cardEntity);
    }

    @Transactional
    public void removeCardFromTrash(CardEntity cardEntity) {
        try {
            if (!cardEntity.isRemoveStatus()) {
                throw new InvalidException(String.format(INVALID,"this card not in trash."));
            }
            cardRepository.deleteById(cardEntity.getId());
        } catch (IllegalArgumentException e) {
            throw new InvalidException(String.format(INVALID,"card id."));
        }
    }

}
