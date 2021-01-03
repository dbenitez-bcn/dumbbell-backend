package com.dumbbell.backend.exercises.domain.aggregates;

import com.dumbbell.backend.exercises.domain.valueObjects.Description;
import com.dumbbell.backend.exercises.domain.valueObjects.Difficulty;
import com.dumbbell.backend.exercises.domain.valueObjects.ExerciseId;
import com.dumbbell.backend.exercises.domain.valueObjects.Name;

public class Exercises {
    private final ExerciseId id;
    private final Name name;
    private final Description description;
    private final Difficulty difficulty;

    public Exercises(String name, String description, int difficulty) {
        this.id = null;
        this.name = new Name(name);
        this.description = new Description(description);
        this.difficulty = new Difficulty(difficulty);
    }
}
