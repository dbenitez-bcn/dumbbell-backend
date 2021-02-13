package com.dumbbell.backend.toggles.domain.valueObjects;

import lombok.Getter;

@Getter
public class Value {
    private final boolean value;

    public Value(boolean value) {
        this.value = value;
    }
}
