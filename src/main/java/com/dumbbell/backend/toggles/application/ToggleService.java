package com.dumbbell.backend.toggles.application;

import com.dumbbell.backend.toggles.domain.aggregates.FeatureToggle;
import com.dumbbell.backend.toggles.domain.exceptions.FeatureToggleAlreadyExist;
import com.dumbbell.backend.toggles.domain.exceptions.FeatureToggleNotFound;
import com.dumbbell.backend.toggles.domain.exceptions.FeatureTogglesNotFound;
import com.dumbbell.backend.toggles.domain.repositories.ToggleRepository;
import com.dumbbell.backend.toggles.domain.valueObjects.Name;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ToggleService {
    private final ToggleRepository repository;

    public FeatureToggle create(String name, boolean value) {
        repository
                .findByName(new Name(name))
                .ifPresent((existingToggle) -> {
                    throw new FeatureToggleAlreadyExist();
                });
        FeatureToggle toggle = new FeatureToggle(name, value);
        return repository.upsert(toggle);
    }

    public List<FeatureToggle> getAll() {
        List<FeatureToggle> list = repository.findAll();
        if (list.isEmpty()) throw new FeatureTogglesNotFound();
        return list;
    }

    public FeatureToggle findByName(String name) {
        return repository
                .findByName(new Name(name))
                .orElseThrow(FeatureToggleNotFound::new);
    }
}
