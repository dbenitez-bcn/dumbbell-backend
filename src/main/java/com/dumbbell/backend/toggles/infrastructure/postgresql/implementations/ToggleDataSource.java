package com.dumbbell.backend.toggles.infrastructure.postgresql.implementations;

import com.dumbbell.backend.toggles.infrastructure.postgresql.entities.FeatureToggleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ToggleDataSource extends JpaRepository<FeatureToggleEntity, String> {
}
