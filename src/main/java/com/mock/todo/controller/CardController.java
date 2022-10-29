package com.mock.todo.controller;

import com.mock.todo.entity.CardEntity;
import com.mock.todo.model.CardModel;
import com.mock.todo.service.impl.CardServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
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
@RestController
@RequiredArgsConstructor
@RequestMapping("/rest/card")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class CardController {

    private final CardServiceImpl cardService;

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
    public CardEntity createCard(@RequestBody CardModel cardModel) {
        return new CardEntity();
    }

    @PutMapping()
    @ResponseStatus(HttpStatus.OK)
    public CardEntity updateCard(@RequestBody CardModel cardModel) {
        return new CardEntity();
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeCard(@PathVariable String id) {
        CardEntity cardEntity = cardService.getCardById(id);
        cardService.removeCardFromBoard(cardEntity);
    }
}
