package com.dumbbell.backend.toggles.infrastructure.postgresql.implementations;

import com.dumbbell.backend.toggles.domain.aggregates.FeatureToggle;
import com.dumbbell.backend.toggles.domain.valueObjects.Name;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.List;
import java.util.Optional;

import static com.dumbbell.backend.toggles.FeatureToggleFixture.*;
import static java.util.Collections.emptyList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

class PostgresToggleRepositoryTest {
    @Mock
    private ToggleDataSource dataSource;
    @InjectMocks
    private PostgresToggleRepository sut;

    @BeforeEach
    void setUp() {
        initMocks(this);
    }

    @Test
    void upsert_shouldSaveTheEntity() {
        when(dataSource.save(testToggleEntity())).thenReturn(testToggleEntity());

        FeatureToggle result = sut.upsert(testToggle());

        assertThat(result).isEqualTo(testToggle());
    }

    @Test
    void findByName_whenToggleExist_shouldReturnTheToggle() {
        when(dataSource.findById(TOGGLE_NAME)).thenReturn(Optional.of(testToggleEntity()));

        Optional<FeatureToggle> result = sut.findByName(new Name(TOGGLE_NAME));

        assertThat(result).contains(testToggle());
    }

    @Test
    void findByName_whenToggleDoNotExist_shouldReturnNothing() {
        when(dataSource.findById(anyString())).thenReturn(Optional.empty());

        Optional<FeatureToggle> result = sut.findByName(new Name(TOGGLE_NAME));

        assertThat(result).isEmpty();
    }

    @Test
    void findAll_shouldReturnAllToggles() {
        when(dataSource.findAll()).thenReturn(List.of(testToggleEntity()));

        List<FeatureToggle> got = sut.findAll();

        assertThat(got).contains(testToggle());
    }

    @Test
    void findAll_whenNoTogglesFound_shouldReturnAnEmptyList() {
        when(dataSource.findAll()).thenReturn(emptyList());

        List<FeatureToggle> got = sut.findAll();

        assertThat(got).isEmpty();
    }

    @Test
    void delete_shouldDeleteTheToggleForTheGivenName() {
        sut.delete(new Name(TOGGLE_NAME));

        verify(dataSource).deleteById(TOGGLE_NAME);
    }
}