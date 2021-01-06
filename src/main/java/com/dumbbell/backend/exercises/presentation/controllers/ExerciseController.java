package com.dumbbell.backend.exercises.presentation.controllers;

import com.dumbbell.backend.exercises.application.ExerciseService;
import com.dumbbell.backend.exercises.domain.aggregates.Exercise;
import com.dumbbell.backend.exercises.presentation.request.ExerciseRequest;
import com.dumbbell.backend.exercises.presentation.responses.ExerciseResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@RequiredArgsConstructor
public class ExerciseController {
    private final ExerciseService exerciseService;

    public ResponseEntity<ExerciseResponse> create(ExerciseRequest exerciseRequest) {
        Exercise exercise = exerciseService.create(
                exerciseRequest.name,
                exerciseRequest.description,
                exerciseRequest.difficulty
        );
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(mapExerciseToResponse(exercise));
    }

    public ResponseEntity<ExerciseResponse> getById(int id) {
        Exercise exercise = exerciseService.getById(id);
        return ResponseEntity.ok(mapExerciseToResponse(exercise));
    }

    private ExerciseResponse mapExerciseToResponse(Exercise exercise) {
        return new ExerciseResponse(
                exercise.getId(),
                exercise.getName(),
                exercise.getDescription(),
                exercise.getDifficulty()
        );
    }
}
