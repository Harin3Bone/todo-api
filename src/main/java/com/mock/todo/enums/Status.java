package com.mock.todo.enums;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@Getter
public enum Status {

    TODO(0),
    IN_PROGRESS(1),
    DONE(2);

    private final int keyId;

    private static final Map<Integer, Status> map;

    static {
        map = new HashMap<>();
        for (Status status : Status.values()) {
            map.put(status.keyId, status);
        }
    }

    Status(int id) {
        this.keyId = id;
    }

    public static Status findByKey(int i) {
        return map.get(i);
    }

}
