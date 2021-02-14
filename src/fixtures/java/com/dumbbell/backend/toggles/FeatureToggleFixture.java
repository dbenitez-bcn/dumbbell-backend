package com.dumbbell.backend.toggles;

import com.dumbbell.backend.toggles.domain.aggregates.FeatureToggle;
import com.dumbbell.backend.toggles.infrastructure.postgresql.entities.FeatureToggleEntity;

public class FeatureToggleFixture {
    public static final String TOGGLE_NAME = "DEFAULT_TOGGLE_NAME";
    public static final boolean TOGGLE_VALUE = true;

    public static FeatureToggle testToggle() {
        return new FeatureToggle(TOGGLE_NAME, TOGGLE_VALUE);
    }

    public static FeatureToggleEntity testToggleEntity() {
        return new FeatureToggleEntity(TOGGLE_NAME, TOGGLE_VALUE);
    }
}
