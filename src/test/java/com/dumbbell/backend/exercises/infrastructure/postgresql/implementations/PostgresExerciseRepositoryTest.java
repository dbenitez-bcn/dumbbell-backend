package com.dumbbell.backend.exercises.infrastructure.postgresql.implementations;

import com.dumbbell.backend.exercises.domain.aggregates.Exercise;
import com.dumbbell.backend.exercises.domain.dtos.ExercisesPageDto;
import com.dumbbell.backend.exercises.infrastructure.postgresql.entities.ExerciseEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.List;
import java.util.Optional;

import static com.dumbbell.backend.exercises.ExerciseEntityFixture.AN_EXERCISE_ID;
import static com.dumbbell.backend.exercises.ExerciseEntityFixture.MUSCLE_UP_ENTITY;
import static com.dumbbell.backend.exercises.ExerciseFixture.MUSCLE_UP;
import static com.dumbbell.backend.exercises.ExerciseFixture.NEW_EXERCISE;
import static java.util.Collections.emptyList;
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

        assertThat(result).isNotEmpty();
        assertEquality(MUSCLE_UP, result.get());
    }


    @Test
    void getById_whenNoExerciseIsFound_shouldReturnNothing() {
        willFindNoExercise();

        Optional<Exercise> result = sut.getById(AN_EXERCISE_ID);

        assertThat(result).isEmpty();
    }

    @Test
    void getAll_shouldReturnAnExerciseList() {
        PageImpl page = new PageImpl(List.of(MUSCLE_UP_ENTITY));
        when(dataSource.findAll(PageRequest.of(1, 10))).thenReturn(page);

        ExercisesPageDto result = sut.getAll(1, 10);

        assertThat(result.exercises).hasSize(1);
        assertEquality(MUSCLE_UP, result.exercises.get(0));
        assertThat(result.pagesCount).isEqualTo(page.getTotalPages());
    }

    @Test
    void getAll_whenNoExerciseAreFound_shouldReturnAnEmptyList() {
        when(dataSource.findAll(PageRequest.of(2, 20))).thenReturn(new PageImpl(emptyList()));

        ExercisesPageDto result = sut.getAll(2, 20);

        assertThat(result.exercises).isEmpty();
    }

    @Test
    void delete_shouldDeleteAnExercise() {
        sut.delete(AN_EXERCISE_ID);

        verify(dataSource).deleteById(AN_EXERCISE_ID);
    }

    private void willFindAnExercise(ExerciseEntity entity) {
        when(dataSource.findById(entity.getId())).thenReturn(Optional.of(entity));
    }

    private void willFindNoExercise() {
        when(dataSource.findById(anyInt())).thenReturn(Optional.empty());
    }

    private void assertEquality(Exercise expected, Exercise result) {
        assertThat(expected.getId()).isEqualTo(result.getId());
        assertThat(expected.getName()).isEqualTo(result.getName());
        assertThat(expected.getDescription()).isEqualTo(result.getDescription());
        assertThat(expected.getDifficulty()).isEqualTo(result.getDifficulty());
    }

}