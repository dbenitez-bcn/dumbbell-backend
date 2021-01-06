package com.dumbbell.backend.exercises.domain.repositories;

import com.dumbbell.backend.exercises.domain.aggregates.Exercise;

import java.util.List;
import java.util.Optional;

public interface ExerciseRepository {
    Exercise upsert(Exercise exercise);

    Optional<Exercise> getById(int id);

    List<Exercise> getAll();

    void delete(int id);
}
