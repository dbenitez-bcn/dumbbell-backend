package com.dumbbell.backend.exercises.domain.repositories;

import com.dumbbell.backend.exercises.domain.aggregates.Exercise;

import java.util.Optional;

public interface ExerciseRepository {
    Exercise upsert(Exercise exercise);

    Optional<Exercise> getById(int id);
}
