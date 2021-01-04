package com.dumbbell.backend.exercises.presentation.responses;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ExerciseResponse {
    public final Integer id;
    public final String name;
    public final String description;
    public final Integer difficulty;
}
