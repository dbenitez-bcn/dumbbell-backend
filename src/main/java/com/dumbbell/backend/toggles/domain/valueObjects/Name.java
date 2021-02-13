package com.dumbbell.backend.toggles.domain.valueObjects;

import lombok.Getter;

@Getter
public class Name {
    private final String value;

    public Name(String value) {
        this.value = value;
    }
}
