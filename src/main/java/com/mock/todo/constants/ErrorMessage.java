package com.mock.todo.constants;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class ErrorMessage {

    public static final String NOT_FOUND = "Card not found for id: %s.";
    public static final String INVALID = "Invalid %s";
}