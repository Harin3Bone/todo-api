package com.mock.todo.service;

import com.mock.todo.entity.CardEntity;
import com.mock.todo.model.CardModel;

import javax.transaction.Transactional;
import java.util.List;

public interface CardService {

    List<CardEntity> listAllCard();
    List<CardEntity> listCardFilterByRemoveStatus(boolean isRemove);

    CardEntity getCardById(String id);

    @Transactional
    CardEntity insertCardToBoard(CardModel cardModel);

    @Transactional
    CardEntity updateCardById(String id, CardModel cardModel);

    @Transactional
    void updateCardRemoveStatus(String id, boolean removeStatus);

    @Transactional
    void removeCardFromTrash(String id);
}
