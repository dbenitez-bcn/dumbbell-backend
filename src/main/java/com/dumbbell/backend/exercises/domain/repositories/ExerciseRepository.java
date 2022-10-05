package com.dumbbell.backend.exercises.domain.repositories;

import com.dumbbell.backend.exercises.domain.aggregates.Exercise;
import com.dumbbell.backend.exercises.domain.dtos.ExercisesPageDto;

import java.util.Optional;

public interface ExerciseRepository {
    Exercise upsert(Exercise exercise);

    Optional<Exercise> getById(int id);

    ExercisesPageDto getAll(int page, int size);

    void delete(int id);
}
