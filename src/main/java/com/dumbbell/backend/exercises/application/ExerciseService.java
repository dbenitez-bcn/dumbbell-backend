package com.dumbbell.backend.exercises.application;

import com.dumbbell.backend.exercises.domain.aggregates.Exercise;
import com.dumbbell.backend.exercises.domain.dtos.ExercisesPageDto;
import com.dumbbell.backend.exercises.domain.exceptions.ExerciseNotFound;
import com.dumbbell.backend.exercises.domain.exceptions.ExercisesNotFound;
import com.dumbbell.backend.exercises.domain.repositories.ExerciseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ExerciseService {
    private final ExerciseRepository exerciseRepository;

    public Exercise create(String name, String description, int difficulty) {
        return exerciseRepository.upsert(
                new Exercise(name, description, difficulty)
        );
    }

    public Exercise getById(int id) {
        return exerciseRepository
                .getById(id)
                .orElseThrow(ExerciseNotFound::new);
    }

    public ExercisesPageDto getAll(int page, int size) {
        ExercisesPageDto exercises = exerciseRepository.getAll(page, size);
        if (exercises.exercises.isEmpty()) throw new ExercisesNotFound();
        return exercises;
    }

    public void delete(int id) {
        getById(id);
        exerciseRepository.delete(id);
    }

    public void update(int id, String name, String description, int difficulty) {
        Exercise exercise = getById(id);
        exercise.update(name, description, difficulty);
        exerciseRepository.upsert(exercise);
    }
}
