package com.dumbbell.backend.toggles.domain.repositories;

import com.dumbbell.backend.toggles.domain.aggregates.FeatureToggle;
import com.dumbbell.backend.toggles.domain.valueObjects.Name;

import java.util.Optional;

public interface ToggleRepository {
    FeatureToggle upsert(FeatureToggle toggle);
    Optional<FeatureToggle> findByName(Name name);
}
