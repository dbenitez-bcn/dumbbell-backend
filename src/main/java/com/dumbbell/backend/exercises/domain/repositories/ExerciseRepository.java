package com.dumbbell.backend.exercises.domain.repositories;

import com.dumbbell.backend.exercises.domain.aggregates.Exercise;

public interface ExerciseRepository {
    Exercise upsert(Exercise exercise);
}
