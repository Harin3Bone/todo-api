package com.mock.todo.controller;

import com.mock.todo.entity.CardEntity;
import com.mock.todo.model.CardModel;
import com.mock.todo.service.impl.CardServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
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
@Tag(name = "Card")
@RequiredArgsConstructor
@RequestMapping("/rest/card")
public class CardController {

    private final CardServiceImpl cardService;

    @Operation(summary = "Get all cards", description = "Get all card in system.")
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<CardEntity> getAllCardInSystem() {
        return cardService.listAllCard();
    }

    @Operation(summary = "Get all available cards", description = "Get all card on board.")
    @GetMapping("/board")
    @ResponseStatus(HttpStatus.OK)
    public List<CardEntity> getAllCardOnBoard() {
        return cardService.listCardFilterByRemoveStatus(false);
    }

    @Operation(summary = "Get all unavailable cards", description = "Get all card on trash.")
    @GetMapping("/trash")
    @ResponseStatus(HttpStatus.OK)
    public List<CardEntity> getAllCardOnTrash() {
        return cardService.listCardFilterByRemoveStatus(true);
    }

    @Operation(summary = "Get card by ID", description = "Get one card by UUID.")
    @Parameter(name = "id", description = "Card ID", in = ParameterIn.PATH)
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public CardEntity getCardById(@PathVariable String id) {
        return cardService.getCardById(id);
    }

    @Operation(summary = "Create available card", description = "Create new card on board.")
    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public CardEntity createCard(@Validated @RequestBody CardModel cardModel) {
        return cardService.insertCardToBoard(cardModel);
    }

    @Operation(summary = "Update card by ID", description = "Update card by UUID.")
    @Parameter(name = "id", description = "Card ID", in = ParameterIn.PATH)
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public CardEntity updateCard(@PathVariable String id, @Validated @RequestBody CardModel cardModel) {
        return cardService.updateCardById(id, cardModel);
    }

    @Operation(summary = "Delete available card by ID", description = "Remove card from board. (Soft delete)")
    @Parameter(name = "id", description = "Card ID", in = ParameterIn.PATH)
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeCardFromBoard(@PathVariable String id) {
        cardService.updateCardRemoveStatus(id, true);
    }

    @Operation(summary = "Restore unavailable card by ID", description = "Restore card from trash to board.")
    @Parameter(name = "id", description = "Card ID", in = ParameterIn.PATH)
    @PutMapping("/trash/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void restoreCardToBoard(@PathVariable String id) {
        cardService.updateCardRemoveStatus(id, false);
    }

    @Operation(summary = "Delete unavailable card by ID", description = "Delete card from trash (Hard delete)")
    @Parameter(name = "id", description = "Card ID", in = ParameterIn.PATH)
    @DeleteMapping("/trash/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeCardFromTrash(@PathVariable String id) {
        cardService.removeCardFromTrash(id);
    }
}
