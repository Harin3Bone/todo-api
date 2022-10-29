package com.mock.todo.service.impl;

import com.mock.todo.entity.CardEntity;
import com.mock.todo.exception.InvalidException;
import com.mock.todo.exception.NotFoundException;
import com.mock.todo.repository.CardRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class CardServiceImpl {

    private static final String NOT_FOUND = "Card not found for id: %s.";
    private static final String INVALID_ID = "Invalid card id.";

    private final CardRepository cardRepository;

    public List<CardEntity> listCardFilterByRemoveStatus(boolean isRemove) {
        return cardRepository.findAllByRemoveStatusIs(isRemove);
    }

    public CardEntity getCardById(String id) {
        try {
            return cardRepository.findById(UUID.fromString(id)).orElseThrow(
                    () -> new NotFoundException(String.format(NOT_FOUND, id))
            );
        } catch (IllegalArgumentException e) {
            throw new InvalidException(INVALID_ID);
        }
    }

    @Transactional
    public void removeCardFromBoard(CardEntity cardEntity) {
        cardEntity.setRemoveStatus(true);
        cardRepository.saveAndFlush(cardEntity);
    }

}
