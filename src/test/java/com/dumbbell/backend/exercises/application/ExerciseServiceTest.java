package com.dumbbell.backend.exercises.application;

import com.dumbbell.backend.exercises.domain.aggregates.Exercise;
import com.dumbbell.backend.exercises.domain.repositories.ExerciseRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static com.dumbbell.backend.exercises.ExerciseFixture.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

class ExerciseServiceTest {

    @Mock
    private ExerciseRepository repository;

    @InjectMocks
    private ExerciseService sut;

    @BeforeEach
    void setUp() {
        initMocks(this);
    }

    @Test
    void create_shouldCreateTheExercise() {
        Exercise expectedExercise = muscleUp();
        when(repository.upsert(customExercise().withId(null).build())).thenReturn(expectedExercise);

        Exercise result = sut.create(AN_EXERCISE_NAME, AN_EXERCISE_DESCRIPTION, AN_EXERCISE_DIFFICULTY);

        assertThat(result).isEqualTo(expectedExercise);
    }
}