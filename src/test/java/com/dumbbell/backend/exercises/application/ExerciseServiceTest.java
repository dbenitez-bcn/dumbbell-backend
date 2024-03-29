package com.dumbbell.backend.exercises.application;

import com.dumbbell.backend.exercises.domain.aggregates.Exercise;
import com.dumbbell.backend.exercises.domain.dtos.ExercisesPageDto;
import com.dumbbell.backend.exercises.domain.exceptions.ExerciseNotFound;
import com.dumbbell.backend.exercises.domain.exceptions.ExercisesNotFound;
import com.dumbbell.backend.exercises.domain.repositories.ExerciseRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.List;
import java.util.Optional;

import static com.dumbbell.backend.exercises.ExerciseFixture.*;
import static java.util.Collections.emptyList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;
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
        Exercise expectedExercise = MUSCLE_UP;
        when(repository.upsert(customExercise().withId(null).build())).thenReturn(expectedExercise);

        Exercise result = sut.create(AN_EXERCISE_NAME, AN_EXERCISE_DESCRIPTION, AN_EXERCISE_DIFFICULTY);

        assertThat(result).isEqualTo(expectedExercise);
    }

    @Test
    void getById_shouldGetAnExercise() {
        willFindAnExercise(MUSCLE_UP);

        Exercise result = sut.getById(AN_EXERCISE_ID);

        assertThat(result).isEqualTo(MUSCLE_UP);
    }

    @Test
    void getById_whenNoExerciseIsFound_shouldFail() {
        willFindNoExercise();

        assertThatThrownBy(() -> sut.getById(AN_EXERCISE_ID))
                .isInstanceOf(ExerciseNotFound.class);
    }

    @Test
    void getAll_shouldReturnAllExercises() {
        when(repository.getAll(1, 10)).thenReturn(new ExercisesPageDto(List.of(MUSCLE_UP), 0));

        ExercisesPageDto result = sut.getAll(1, 10);

        assertThat(result.exercises).containsOnly(MUSCLE_UP);
    }

    @Test
    void getAll_whenNoExerciseIsFound_shouldFail() {
        when(repository.getAll(2, 20)).thenReturn(new ExercisesPageDto(emptyList(), 0));

        assertThatThrownBy(() -> sut.getAll(2, 20))
                .isInstanceOf(ExercisesNotFound.class);
    }

    @Test
    void delete_shouldDeleteAnExercise() {
        willFindAnExercise(MUSCLE_UP);

        sut.delete(AN_EXERCISE_ID);

        verify(repository).delete(AN_EXERCISE_ID);
    }

    @Test
    void delete_whenExerciseDontExist_shouldFail() {
        willFindNoExercise();

        assertThatThrownBy(() -> sut.delete(AN_EXERCISE_ID))
                .isInstanceOf(ExerciseNotFound.class);

        verify(repository, never()).delete(anyInt());
    }

    @Test
    void update_shouldUpdateAnExercise() {
        ArgumentCaptor<Exercise> exerciseCaptor = ArgumentCaptor.forClass(Exercise.class);
        willFindAnExercise(customExercise().withId(AN_EXERCISE_ID).build());

        sut.update(
                AN_EXERCISE_ID,
                "new name",
                "new description",
                2
        );

        verify(repository).upsert(exerciseCaptor.capture());
        Exercise capturedExercise = exerciseCaptor.getValue();
        assertThat(capturedExercise.getId()).isEqualTo(AN_EXERCISE_ID);
        assertThat(capturedExercise.getName()).isEqualTo("new name");
        assertThat(capturedExercise.getDescription()).isEqualTo("new description");
        assertThat(capturedExercise.getDifficulty()).isEqualTo(2);
    }

    @Test
    void update_whenExerciseDoesntExist_shouldFail() {
        willFindNoExercise();

        assertThatThrownBy(
                () -> sut.update(AN_EXERCISE_ID, AN_EXERCISE_NAME, AN_EXERCISE_DESCRIPTION, AN_EXERCISE_DIFFICULTY)
        ).isInstanceOf(ExerciseNotFound.class);
    }

    private void willFindAnExercise(Exercise exercise) {
        when(repository.getById(exercise.getId())).thenReturn(Optional.of(exercise));
    }

    private void willFindNoExercise() {
        when(repository.getById(anyInt())).thenReturn(Optional.empty());
    }
}