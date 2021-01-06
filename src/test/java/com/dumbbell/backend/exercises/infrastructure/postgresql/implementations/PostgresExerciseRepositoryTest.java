package com.dumbbell.backend.exercises.infrastructure.postgresql.implementations;

import com.dumbbell.backend.exercises.domain.aggregates.Exercise;
import com.dumbbell.backend.exercises.infrastructure.postgresql.entities.ExerciseEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.Optional;

import static com.dumbbell.backend.exercises.ExerciseEntityFixture.AN_EXERCISE_ID;
import static com.dumbbell.backend.exercises.ExerciseEntityFixture.MUSCLE_UP_ENTITY;
import static com.dumbbell.backend.exercises.ExerciseFixture.MUSCLE_UP;
import static com.dumbbell.backend.exercises.ExerciseFixture.NEW_EXERCISE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

class PostgresExerciseRepositoryTest {

    @Mock
    private ExerciseDataSource dataSource;

    @InjectMocks
    private PostgresExerciseRepository sut;

    @Captor
    private ArgumentCaptor<ExerciseEntity> exerciseCaptor;

    @BeforeEach
    void setUp() {
        initMocks(this);
    }

    @Test
    void upsert_shouldCreateANewExercise() {
        when(dataSource.save(any(ExerciseEntity.class))).thenReturn(MUSCLE_UP_ENTITY);

        Exercise result = sut.upsert(NEW_EXERCISE);

        assertThat(result).isEqualTo(MUSCLE_UP);
        verify(dataSource).save(exerciseCaptor.capture());
        ExerciseEntity capturedExercise = exerciseCaptor.getValue();
        assertThat(capturedExercise.getId()).isEqualTo(NEW_EXERCISE.getId());
        assertThat(capturedExercise.getName()).isEqualTo(NEW_EXERCISE.getName());
        assertThat(capturedExercise.getDescription()).isEqualTo(NEW_EXERCISE.getDescription());
        assertThat(capturedExercise.getDifficulty()).isEqualTo(NEW_EXERCISE.getDifficulty());
    }

    @Test
    void getById_whenExerciseIsFound_shouldReturnAnExercise() {
        willFindAnExercise(MUSCLE_UP_ENTITY);

        Optional<Exercise> result = sut.getById(AN_EXERCISE_ID);

        assertThat(result).contains(MUSCLE_UP);
    }


    @Test
    void getById_whenNoExerciseIsFound_shouldReturnNothing() {
        willFindNoExercise();

        Optional<Exercise> result = sut.getById(AN_EXERCISE_ID);

        assertThat(result).isEmpty();
    }

    private void willFindAnExercise(ExerciseEntity entity) {
        when(dataSource.findById(entity.getId())).thenReturn(Optional.of(entity));
    }

    private void willFindNoExercise() {
        when(dataSource.findById(anyInt())).thenReturn(Optional.empty());
    }
}