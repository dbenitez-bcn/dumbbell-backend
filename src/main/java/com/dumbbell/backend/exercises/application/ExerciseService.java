package com.dumbbell.backend.exercises.application;

import com.dumbbell.backend.exercises.domain.aggregates.Exercise;
import com.dumbbell.backend.exercises.domain.repositories.ExerciseRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ExerciseService {
    private final ExerciseRepository exerciseRepository;

    public Exercise create(String name, String description, int difficulty) {
        return exerciseRepository.upsert(
                new Exercise(name, description, difficulty)
        );
    }

    public Exercise getById(int id) {
        return null;
    }
}
