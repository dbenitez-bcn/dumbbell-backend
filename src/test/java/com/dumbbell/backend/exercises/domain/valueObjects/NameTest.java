package com.dumbbell.backend.exercises.domain.valueObjects;

import com.dumbbell.backend.exercises.domain.exceptions.InvalidName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

class NameTest {
    @Test
    void whenValueIsNull_shouldFail() {
        assertThrows(
                InvalidName.class,
                () -> new Name(null)
        );
    }

    @Test
    void whenValueIsEmpty_shouldFail() {
        assertThrows(
                InvalidName.class,
                () -> new Name("")
        );
    }
}