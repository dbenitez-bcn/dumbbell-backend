package com.dumbbell.backend.exercises.presentation.controllers;

import com.dumbbell.backend.exercises.application.ExerciseService;
import com.dumbbell.backend.exercises.domain.dtos.ExercisesPageDto;
import com.dumbbell.backend.exercises.presentation.request.ExerciseRequest;
import com.dumbbell.backend.exercises.presentation.responses.ExerciseResponse;
import com.dumbbell.backend.exercises.presentation.responses.ExercisesPageResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static com.dumbbell.backend.exercises.ExerciseFixture.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

class ExerciseControllerTest {

    private static final ExerciseRequest EXERCISE_REQUEST =
            new ExerciseRequest(AN_EXERCISE_NAME, AN_EXERCISE_DESCRIPTION, AN_EXERCISE_DIFFICULTY);

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

        ResponseEntity<ExerciseResponse> result = sut.create(EXERCISE_REQUEST);

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
        when(exerciseService.getAll(1, 10)).thenReturn(new ExercisesPageDto(List.of(MUSCLE_UP), 0));

        ResponseEntity<ExercisesPageResponse> result = sut.getAll(1, 10);

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
        ExerciseResponse resultBody = result.getBody().exercises.get(0);
        assertThat(resultBody.id).isEqualTo(MUSCLE_UP.getId());
        assertThat(resultBody.name).isEqualTo(MUSCLE_UP.getName());
        assertThat(resultBody.description).isEqualTo(MUSCLE_UP.getDescription());
        assertThat(resultBody.difficulty).isEqualTo(MUSCLE_UP.getDifficulty());
        assertThat(result.getBody().pagesCount).isEqualTo(0);
    }

    @Test
    void delete_shouldDeleteTheExercise() {
        ResponseEntity result = sut.delete(AN_EXERCISE_ID);

        verify(exerciseService).delete(AN_EXERCISE_ID);
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
    }

    @Test
    void update_shouldUpdateAnExercise() {
        ResponseEntity result = sut.update(
                AN_EXERCISE_ID,
                EXERCISE_REQUEST
        );

        verify(exerciseService)
                .update(AN_EXERCISE_ID, AN_EXERCISE_NAME, AN_EXERCISE_DESCRIPTION, AN_EXERCISE_DIFFICULTY);
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);

    }
}