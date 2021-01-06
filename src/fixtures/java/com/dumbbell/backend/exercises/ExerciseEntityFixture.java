package com.dumbbell.backend.exercises;

import com.dumbbell.backend.exercises.infrastructure.postgresql.entities.ExerciseEntity;

public class ExerciseEntityFixture {
    public static final Integer AN_EXERCISE_ID = 23;
    public static final String AN_EXERCISE_NAME = "Muscle up";
    public static final String AN_EXERCISE_DESCRIPTION = "First do a pull up and after passing your chest over the bar do a dip.";
    public static final Integer AN_EXERCISE_DIFFICULTY = 8;

    public static ExerciseEntityBuilder customExerciseEntity() {
        return new ExerciseEntityBuilder();
    }

    public static ExerciseEntity muscleUpEntity() {
        return customExerciseEntity()
                .withId(AN_EXERCISE_ID)
                .withName(AN_EXERCISE_NAME)
                .withDescription(AN_EXERCISE_DESCRIPTION)
                .withDifficulty(AN_EXERCISE_DIFFICULTY)
                .build();
    }

    public static class ExerciseEntityBuilder {
        private Integer id = 23;
        private String name = "Muscle up";
        private String description = "First do a pull up and after passing your chest over the bar do a dip.";
        private Integer difficulty = 8;

        public ExerciseEntityBuilder withId(Integer value) {
            this.id = value;
            return this;
        }

        public ExerciseEntityBuilder withName(String value) {
            this.name = value;
            return this;
        }

        public ExerciseEntityBuilder withDescription(String value) {
            this.description = value;
            return this;
        }

        public ExerciseEntityBuilder withDifficulty(int value) {
            this.difficulty = value;
            return this;
        }

        public ExerciseEntity build() {
            return new ExerciseEntity(this.id, this.name, this.description, this.difficulty);
        }
    }
}
