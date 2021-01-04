package com.dumbbell.backend.exercises.presentation.request;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ExerciseRequest {
    public final String name;
    public final String description;
    public final Integer difficulty;
}
