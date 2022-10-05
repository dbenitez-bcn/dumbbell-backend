package com.dumbbell.backend.exercises.infrastructure.postgresql.implementations;

import com.dumbbell.backend.exercises.domain.aggregates.Exercise;
import com.dumbbell.backend.exercises.domain.dtos.ExercisesPageDto;
import com.dumbbell.backend.exercises.domain.repositories.ExerciseRepository;
import com.dumbbell.backend.exercises.infrastructure.postgresql.entities.ExerciseEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Repository
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
    public ExercisesPageDto getAll(int page, int size) {
        Page<ExerciseEntity> exercisePage = dataSource
                .findAll(PageRequest.of(page, size));
        return new ExercisesPageDto(
                exercisePage
                    .stream()
                    .map(this::entityToExercise)
                    .collect(Collectors.toList()),
                exercisePage.getTotalPages());
    }

    @Override
    public void delete(int id) {
        dataSource.deleteById(id);
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
