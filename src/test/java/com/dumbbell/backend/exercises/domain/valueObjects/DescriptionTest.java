package com.dumbbell.backend.exercises.domain.valueObjects;

import com.dumbbell.backend.exercises.domain.exceptions.InvalidDescription;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

class DescriptionTest {
    @Test
    void whenValueIsNull_shouldFail() {
        assertThrows(
                InvalidDescription.class,
                () -> new Description(null)
        );
    }

    @Test
    void whenValueIsEmpty_shouldFail() {
        assertThrows(
                InvalidDescription.class,
                () -> new Description("")
        );
    }
}