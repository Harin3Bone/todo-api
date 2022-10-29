package com.mock.todo.enums;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@Getter
public enum CardStatus {

    TODO(0),
    IN_PROGRESS(1),
    DONE(2);

    private final int keyId;

    private static final Map<Integer, CardStatus> map;

    static {
        map = new HashMap<>();
        for (CardStatus status : CardStatus.values()) {
            map.put(status.keyId, status);
        }
    }

    CardStatus(int id) {
        this.keyId = id;
    }

    public static CardStatus findByKey(int i) {
        return map.get(i);
    }

}
