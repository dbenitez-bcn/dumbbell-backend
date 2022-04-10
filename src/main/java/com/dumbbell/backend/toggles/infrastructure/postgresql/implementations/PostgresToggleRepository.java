package com.dumbbell.backend.toggles.infrastructure.postgresql.implementations;

import com.dumbbell.backend.toggles.domain.aggregates.FeatureToggle;
import com.dumbbell.backend.toggles.domain.repositories.ToggleRepository;
import com.dumbbell.backend.toggles.domain.valueObjects.Name;
import com.dumbbell.backend.toggles.infrastructure.postgresql.entities.FeatureToggleEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class PostgresToggleRepository implements ToggleRepository {
    private final ToggleDataSource dataSource;

    @Override
    public FeatureToggle upsert(FeatureToggle toggle) {
        FeatureToggleEntity entity = dataSource.save(new FeatureToggleEntity(toggle.getName(), toggle.getValue()));
        return mapFromEntity(entity);
    }

    @Override
    public Optional<FeatureToggle> findByName(Name name) {
        return dataSource
                .findById(name.getValue())
                .map(this::mapFromEntity);
    }

    @Override
    public List<FeatureToggle> findAll() {
        return dataSource
                .findAll()
                .stream()
                .map(this::mapFromEntity)
                .collect(Collectors.toList());
    }

    private FeatureToggle mapFromEntity(FeatureToggleEntity entity) {
        return new FeatureToggle(entity.getName(), entity.isValue());
    }
}
