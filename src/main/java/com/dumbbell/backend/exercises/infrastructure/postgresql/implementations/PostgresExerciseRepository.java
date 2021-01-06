package com.dumbbell.backend.exercises.infrastructure.postgresql.implementations;

import com.dumbbell.backend.exercises.domain.aggregates.Exercise;
import com.dumbbell.backend.exercises.domain.repositories.ExerciseRepository;
import com.dumbbell.backend.exercises.infrastructure.postgresql.entities.ExerciseEntity;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class PostgresExerciseRepository implements ExerciseRepository {

    private final ExerciseDataSource dataSource;

    @Override
    public Exercise upsert(Exercise exercise) {
        ExerciseEntity savedEntity = dataSource.save(
                new ExerciseEntity(
                        exercise.getId(),
                        exercise.getName(),
                        exercise.getDescription(),
                        exercise.getDifficulty()
                )
        );

        return new Exercise(
                savedEntity.getId(),
                savedEntity.getName(),
                savedEntity.getDescription(),
                savedEntity.getDifficulty()
        );
    }
}
