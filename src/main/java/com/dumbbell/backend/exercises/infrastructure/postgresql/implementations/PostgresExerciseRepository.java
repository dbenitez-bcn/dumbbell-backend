package com.dumbbell.backend.exercises.infrastructure.postgresql.implementations;

import com.dumbbell.backend.exercises.domain.aggregates.Exercise;
import com.dumbbell.backend.exercises.domain.repositories.ExerciseRepository;
import com.dumbbell.backend.exercises.infrastructure.postgresql.entities.ExerciseEntity;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

        return entityToExercise(savedEntity);
    }

    @Override
    public Optional<Exercise> getById(int id) {
        return dataSource
                .findById(id)
                .map(this::entityToExercise);
    }

    @Override
    public List<Exercise> getAll() {
        return dataSource
                .findAll()
                .stream()
                .map(this::entityToExercise)
                .collect(Collectors.toList());
    }

    @Override
    public void delete(int id) {

    }

    private Exercise entityToExercise(ExerciseEntity savedEntity) {
        return new Exercise(
                savedEntity.getId(),
                savedEntity.getName(),
                savedEntity.getDescription(),
                savedEntity.getDifficulty()
        );
    }
}
