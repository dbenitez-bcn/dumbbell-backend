package com.dumbbell.backend.exercises.presentation.controllers;

import com.dumbbell.backend.exercises.application.ExerciseService;
import com.dumbbell.backend.exercises.domain.aggregates.Exercise;
import com.dumbbell.backend.exercises.domain.dtos.ExercisesPageDto;
import com.dumbbell.backend.exercises.presentation.request.ExerciseRequest;
import com.dumbbell.backend.exercises.presentation.responses.ExerciseResponse;
import com.dumbbell.backend.exercises.presentation.responses.ExercisesPageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Collectors;

@RequiredArgsConstructor
@RestController
public class ExerciseController {
    private final ExerciseService exerciseService;

    @PostMapping("/exercise")
    public ResponseEntity<ExerciseResponse> create(@RequestBody ExerciseRequest request) {
        Exercise exercise = exerciseService.create(
                request.name,
                request.description,
                request.difficulty
        );
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(mapExerciseToResponse(exercise));
    }

    @GetMapping("/exercise/{id}")
    public ResponseEntity<ExerciseResponse> getById(@PathVariable("id") int id) {
        Exercise exercise = exerciseService.getById(id);
        return ResponseEntity.ok(mapExerciseToResponse(exercise));
    }

    @GetMapping("/exercise")
    public ResponseEntity<ExercisesPageResponse> getAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        ExercisesPageDto pageDto = exerciseService
                .getAll(page, size);
        return ResponseEntity.ok(
                new ExercisesPageResponse(
                        pageDto
                                .exercises
                                .stream()
                                .map(this::mapExerciseToResponse)
                                .collect(Collectors.toList()),
                        pageDto.pagesCount
                )
        );
    }

    @DeleteMapping("/exercise/{id}")
    public ResponseEntity delete(@PathVariable("id") int id) {
        exerciseService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/exercise/{id}")
    public ResponseEntity update(@PathVariable("id") int id, @RequestBody ExerciseRequest request) {
        exerciseService.update(
                id,
                request.name,
                request.description,
                request.difficulty
        );
        return ResponseEntity.noContent().build();
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
