package com.dumbbell.backend.exercises;

import com.dumbbell.backend.exercises.domain.aggregates.Exercise;

public class ExerciseFixture {
    public static final Integer AN_EXERCISE_ID = 23;
    public static final String AN_EXERCISE_NAME = "Muscle up";
    public static final String AN_EXERCISE_DESCRIPTION = "First do a pull up and after passing your chest over the bar do a dip.";
    public static final Integer AN_EXERCISE_DIFFICULTY = 8;

    public static ExerciseBuilder customExercise() {
        return new ExerciseBuilder();
    }

    public static Exercise muscleUp() {
        return customExercise()
                .withId(AN_EXERCISE_ID)
                .withName(AN_EXERCISE_NAME)
                .withDescription(AN_EXERCISE_DESCRIPTION)
                .withDifficulty(AN_EXERCISE_DIFFICULTY)
                .build();
    }

    public static Exercise newExercise() {
        return customExercise()
                .withId(null)
                .withName(AN_EXERCISE_NAME)
                .withDescription(AN_EXERCISE_DESCRIPTION)
                .withDifficulty(AN_EXERCISE_DIFFICULTY)
                .build();
    }

    public static class ExerciseBuilder {
        private Integer id = 23;
        private String name = "Muscle up";
        private String description = "First do a pull up and after passing your chest over the bar do a dip.";
        private Integer difficulty = 8;

        public ExerciseBuilder withId(Integer value) {
            this.id = value;
            return this;
        }

        public ExerciseBuilder withName(String value) {
            this.name = value;
            return this;
        }

        public ExerciseBuilder withDescription(String value) {
            this.description = value;
            return this;
        }

        public ExerciseBuilder withDifficulty(int value) {
            this.difficulty = value;
            return this;
        }

        public Exercise build() {
            if (this.id == null) return new Exercise(this.name, this.description, this.difficulty);
            return new Exercise(this.id, this.name, this.description, this.difficulty);
        }
    }
}
