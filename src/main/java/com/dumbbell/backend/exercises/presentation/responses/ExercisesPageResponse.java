package com.dumbbell.backend.exercises.presentation.responses;

import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class ExercisesPageResponse {
    public final List<ExerciseResponse> exercises;
    public final int pagesCount;
}
