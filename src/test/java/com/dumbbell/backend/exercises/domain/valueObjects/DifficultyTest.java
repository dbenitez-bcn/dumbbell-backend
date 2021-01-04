package com.dumbbell.backend.exercises.domain.valueObjects;

import com.dumbbell.backend.exercises.domain.exceptions.InvalidDifficulty;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

class DifficultyTest {
    @Test
    void whenValueIsLessThanOne_shouldFail() {
        assertThrows(
                InvalidDifficulty.class,
                () -> new Difficulty(0)
        );
    }
    @Test
    void whenValueIsLessThanTen_shouldFail() {
        assertThrows(
                InvalidDifficulty.class,
                () -> new Difficulty(11)
        );
    }
}