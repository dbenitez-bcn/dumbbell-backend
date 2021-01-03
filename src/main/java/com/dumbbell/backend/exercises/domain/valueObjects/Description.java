package com.dumbbell.backend.exercises.domain.valueObjects;

import com.dumbbell.backend.exercises.domain.exceptions.InvalidDescription;

public class Description {
    private final String value;

    public Description(String value) {
        if (value == null || value.isEmpty()) throw new InvalidDescription();
        this.value = value;
    }
}
