package com.dumbbell.backend.exercises.domain.valueObjects;

import com.dumbbell.backend.exercises.domain.exceptions.InvalidDifficulty;
import lombok.Getter;

@Getter
public class Difficulty {
    private final int value;

    public Difficulty(int value) {
        if (value < 1 || value > 10) throw new InvalidDifficulty();
        this.value = value;
    }
}
