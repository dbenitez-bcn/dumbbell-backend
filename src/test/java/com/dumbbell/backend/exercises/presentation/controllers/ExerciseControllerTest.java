package com.dumbbell.backend.exercises.presentation.controllers;

import com.dumbbell.backend.exercises.application.ExerciseService;
import com.dumbbell.backend.exercises.presentation.request.ExerciseRequest;
import com.dumbbell.backend.exercises.presentation.responses.ExerciseResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static com.dumbbell.backend.exercises.ExerciseFixture.*;
import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
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
        when(exerciseService.create(AN_EXERCISE_NAME, AN_EXERCISE_DESCRIPTION, AN_EXERCISE_DIFFICULTY))
                .thenReturn(MUSCLE_UP);

        ResponseEntity<ExerciseResponse> result = sut.create(
                new ExerciseRequest(AN_EXERCISE_NAME, AN_EXERCISE_DESCRIPTION, AN_EXERCISE_DIFFICULTY)
        );

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        ExerciseResponse resultBody = result.getBody();
        assertThat(resultBody.id).isEqualTo(MUSCLE_UP.getId());
        assertThat(resultBody.name).isEqualTo(MUSCLE_UP.getName());
        assertThat(resultBody.description).isEqualTo(MUSCLE_UP.getDescription());
        assertThat(resultBody.difficulty).isEqualTo(MUSCLE_UP.getDifficulty());
    }

    @Test
    void getById_shouldReturnAnExercise() {
        when(exerciseService.getById(AN_EXERCISE_ID)).thenReturn(MUSCLE_UP);

        ResponseEntity<ExerciseResponse> result = sut.getById(AN_EXERCISE_ID);

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
        ExerciseResponse resultBody = result.getBody();
        assertThat(resultBody.id).isEqualTo(MUSCLE_UP.getId());
        assertThat(resultBody.name).isEqualTo(MUSCLE_UP.getName());
        assertThat(resultBody.description).isEqualTo(MUSCLE_UP.getDescription());
        assertThat(resultBody.difficulty).isEqualTo(MUSCLE_UP.getDifficulty());
    }

    @Test
    void getAll_shouldReturnAllExercises() {
        when(exerciseService.getAll()).thenReturn(asList(MUSCLE_UP));

        ResponseEntity<List<ExerciseResponse>> result = sut.getAll();

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
        ExerciseResponse resultBody = result.getBody().get(0);
        assertThat(resultBody.id).isEqualTo(MUSCLE_UP.getId());
        assertThat(resultBody.name).isEqualTo(MUSCLE_UP.getName());
        assertThat(resultBody.description).isEqualTo(MUSCLE_UP.getDescription());
        assertThat(resultBody.difficulty).isEqualTo(MUSCLE_UP.getDifficulty());

    }

    @Test
    void delete_shouldDeleteTheExercise() {
        ResponseEntity result = sut.delete(AN_EXERCISE_ID);

        verify(exerciseService).delete(AN_EXERCISE_ID);
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
    }
}