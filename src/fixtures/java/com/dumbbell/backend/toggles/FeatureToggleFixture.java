package com.dumbbell.backend.toggles;

import com.dumbbell.backend.toggles.domain.aggregates.FeatureToggle;
import com.dumbbell.backend.toggles.infrastructure.postgresql.entities.FeatureToggleEntity;

public class FeatureToggleFixture {
    public static final String TOGGLE_NAME = "DEFAULT_TOGGLE_NAME";
    public static final boolean TOGGLE_VALUE = true;
    public static ToggleBuilder customToggle() {
        return new ToggleBuilder();
    }

    public static FeatureToggle testToggle() {
        return new ToggleBuilder()
                .withName(TOGGLE_NAME)
                .withValue(TOGGLE_VALUE)
                .build();
    }

    public static class ToggleBuilder {
        private String name = "DEFAULT_TOGGLE_NAME";
        private boolean value = true;

        public ToggleBuilder withName(String name) {
            this.name = name;
            return this;
        }

        public ToggleBuilder withValue(boolean value) {
            this.value = value;
            return this;
        }

        public FeatureToggle build() {
            return new FeatureToggle(name, value);
        }
    }



    public static FeatureToggleEntity testToggleEntity() {
        return new FeatureToggleEntity(TOGGLE_NAME, TOGGLE_VALUE);
    }
}
