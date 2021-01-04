package com.dumbbell.backend.exercises.domain.aggregates;

import com.dumbbell.backend.exercises.domain.valueObjects.Description;
import com.dumbbell.backend.exercises.domain.valueObjects.Difficulty;
import com.dumbbell.backend.exercises.domain.valueObjects.ExerciseId;
import com.dumbbell.backend.exercises.domain.valueObjects.Name;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode
public class Exercise {
    private final ExerciseId id;
    private final Name name;
    private final Description description;
    private final Difficulty difficulty;

    public Exercise(String name, String description, int difficulty) {
        this.id = null;
        this.name = new Name(name);
        this.description = new Description(description);
        this.difficulty = new Difficulty(difficulty);
    }

    public Exercise(int id, String name, String description, int difficulty) {
        this.id = new ExerciseId(id);
        this.name = new Name(name);
        this.description = new Description(description);
        this.difficulty = new Difficulty(difficulty);
    }

    public int getId() {
        if (this.id == null) return 0;
        return this.id.getValue();
    }

    public String getName() {
        return this.name.getValue();
    }

    public String getDescription() {
        return this.description.getValue();
    }

    public int getDifficulty() {
        return this.difficulty.getValue();
    }
}
