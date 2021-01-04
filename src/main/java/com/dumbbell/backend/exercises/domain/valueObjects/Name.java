package com.dumbbell.backend.exercises.domain.valueObjects;

import com.dumbbell.backend.exercises.domain.exceptions.InvalidName;
import lombok.Getter;

@Getter
public class Name {
    private final String value;

    public Name(String value) {
        if (value == null || value.isEmpty()) throw new InvalidName();
        this.value = value;
    }
}
