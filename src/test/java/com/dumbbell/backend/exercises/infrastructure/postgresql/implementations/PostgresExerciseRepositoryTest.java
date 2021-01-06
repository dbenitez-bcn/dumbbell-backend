package com.dumbbell.backend.exercises.infrastructure.postgresql.implementations;

import com.dumbbell.backend.exercises.domain.aggregates.Exercise;
import com.dumbbell.backend.exercises.infrastructure.postgresql.entities.ExerciseEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static com.dumbbell.backend.exercises.ExerciseEntityFixture.muscleUpEntity;
import static com.dumbbell.backend.exercises.ExerciseFixture.MUSCLE_UP;
import static com.dumbbell.backend.exercises.ExerciseFixture.newExercise;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
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
        Exercise exerciseToSave = newExercise();
        when(dataSource.save(any(ExerciseEntity.class))).thenReturn(muscleUpEntity());

        Exercise result = sut.upsert(exerciseToSave);

        assertThat(result).isEqualTo(MUSCLE_UP);
        verify(dataSource).save(exerciseCaptor.capture());
        ExerciseEntity capturedExercise = exerciseCaptor.getValue();
        assertThat(capturedExercise.getId()).isEqualTo(exerciseToSave.getId());
        assertThat(capturedExercise.getName()).isEqualTo(exerciseToSave.getName());
        assertThat(capturedExercise.getDescription()).isEqualTo(exerciseToSave.getDescription());
        assertThat(capturedExercise.getDifficulty()).isEqualTo(exerciseToSave.getDifficulty());
    }
}