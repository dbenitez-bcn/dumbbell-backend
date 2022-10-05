package com.dumbbell.backend.exercises.domain.dtos;

import com.dumbbell.backend.exercises.domain.aggregates.Exercise;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class ExercisesPageDto {
    public final List<Exercise> exercises;
    public final int pagesCount;
}
