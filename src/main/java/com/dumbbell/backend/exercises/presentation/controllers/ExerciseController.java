package com.dumbbell.backend.exercises.presentation.controllers;

import com.dumbbell.backend.exercises.application.ExerciseService;
import com.dumbbell.backend.exercises.domain.aggregates.Exercise;
import com.dumbbell.backend.exercises.presentation.request.ExerciseRequest;
import com.dumbbell.backend.exercises.presentation.responses.ExerciseResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class ExerciseController {
    private final ExerciseService exerciseService;

    public ResponseEntity<ExerciseResponse> create(ExerciseRequest request) {
        Exercise exercise = exerciseService.create(
                request.name,
                request.description,
                request.difficulty
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

    public ResponseEntity<List<ExerciseResponse>> getAll() {
        return ResponseEntity.ok(
                exerciseService
                        .getAll()
                        .stream()
                        .map(this::mapExerciseToResponse)
                        .collect(Collectors.toList())
        );
    }

    public ResponseEntity delete(int id) {
        exerciseService.delete(id);
        return ResponseEntity.noContent().build();
    }

    public ResponseEntity update(int id, ExerciseRequest request) {
        exerciseService.update(
                id,
                request.name,
                request.description,
                request.difficulty
        );
        return ResponseEntity.noContent().build();
    }
}
