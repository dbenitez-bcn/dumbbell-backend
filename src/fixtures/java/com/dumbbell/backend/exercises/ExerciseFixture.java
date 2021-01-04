package com.dumbbell.backend.exercises;

import com.dumbbell.backend.exercises.domain.aggregates.Exercise;

public class ExerciseFixture {
    public static final int AN_EXERCISE_ID = 23;
    public static final String AN_EXERCISE_NAME = "Muscle up";
    public static final String AN_EXERCISE_DESCRIPTION = "First do a pull up and after passing your chest over the bar do a dip.";
    public static final Integer AN_EXERCISE_DIFFICULTY = 8;

    public static Exercise muscleUp() {
        return new Exercise(AN_EXERCISE_ID, AN_EXERCISE_NAME, AN_EXERCISE_DESCRIPTION, AN_EXERCISE_DIFFICULTY);
    }
}
