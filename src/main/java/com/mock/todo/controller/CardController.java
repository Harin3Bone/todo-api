package com.mock.todo.controller;

import com.mock.todo.entity.CardEntity;
import com.mock.todo.model.CardModel;
import com.mock.todo.service.impl.CardServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/rest/card")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class CardController {

    private final CardServiceImpl cardService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<CardEntity> getAllCardInSystem() {
        return cardService.listAllCard();
    }

    @GetMapping("/board")
    @ResponseStatus(HttpStatus.OK)
    public List<CardEntity> getAllCardOnBoard() {
        return cardService.listCardFilterByRemoveStatus(false);
    }

    @GetMapping("/trash")
    @ResponseStatus(HttpStatus.OK)
    public List<CardEntity> getAllCardOnTrash() {
        return cardService.listCardFilterByRemoveStatus(true);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public CardEntity getCardById(@PathVariable String id) {
        return cardService.getCardById(id);
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public CardEntity createCard(@Validated @RequestBody CardModel cardModel) {
        return cardService.insertCardToBoard(cardModel);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public CardEntity updateCard(@PathVariable String id, @Validated @RequestBody CardModel cardModel) {
        return cardService.updateCardById(id, cardModel);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeCardFromBoard(@PathVariable String id) {
        cardService.updateCardRemoveStatus(id, true);
    }

    @PutMapping("/trash/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void restoreCardToBoard(@PathVariable String id) {
        cardService.updateCardRemoveStatus(id, false);
    }

    @DeleteMapping("/trash/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeCardFromTrash(@PathVariable String id) {
        cardService.removeCardFromTrash(id);
    }
}
