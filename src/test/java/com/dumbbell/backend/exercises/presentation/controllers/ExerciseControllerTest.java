package com.dumbbell.backend.exercises.presentation.controllers;

import com.dumbbell.backend.exercises.application.ExerciseService;
import com.dumbbell.backend.exercises.domain.aggregates.Exercise;
import com.dumbbell.backend.exercises.presentation.request.ExerciseRequest;
import com.dumbbell.backend.exercises.presentation.responses.ExerciseResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static com.dumbbell.backend.exercises.ExerciseFixture.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

class ExerciseControllerTest {

    @Mock
    private ExerciseService exerciseService;

    @InjectMocks
    private ExerciseController sut;

    @BeforeEach
    void setUp() {
        initMocks(this);
    }

    @Test
    void create_shouldCreateANewExercise() {
        Exercise muscleUp = muscleUp();
        when(exerciseService.create(AN_EXERCISE_NAME, AN_EXERCISE_DESCRIPTION, AN_EXERCISE_DIFFICULTY))
                .thenReturn(muscleUp);

        ResponseEntity<ExerciseResponse> result = sut.create(
                new ExerciseRequest(AN_EXERCISE_NAME, AN_EXERCISE_DESCRIPTION, AN_EXERCISE_DIFFICULTY)
        );

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        ExerciseResponse resultBody = result.getBody();
        assertThat(resultBody.id).isEqualTo(muscleUp.getId());
        assertThat(resultBody.name).isEqualTo(muscleUp.getName());
        assertThat(resultBody.description).isEqualTo(muscleUp.getDescription());
        assertThat(resultBody.difficulty).isEqualTo(muscleUp.getDifficulty());
    }
}