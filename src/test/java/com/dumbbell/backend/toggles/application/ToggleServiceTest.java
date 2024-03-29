package com.dumbbell.backend.toggles.application;

import com.dumbbell.backend.toggles.domain.aggregates.FeatureToggle;
import com.dumbbell.backend.toggles.domain.exceptions.FeatureToggleAlreadyExist;
import com.dumbbell.backend.toggles.domain.exceptions.FeatureToggleNotFound;
import com.dumbbell.backend.toggles.domain.exceptions.FeatureTogglesNotFound;
import com.dumbbell.backend.toggles.domain.exceptions.InvalidFeatureToggleName;
import com.dumbbell.backend.toggles.domain.repositories.ToggleRepository;
import com.dumbbell.backend.toggles.domain.valueObjects.Name;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.List;
import java.util.Optional;

import static com.dumbbell.backend.toggles.FeatureToggleFixture.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.util.Lists.emptyList;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;

class ToggleServiceTest {
    @Mock
    private ToggleRepository toggleRepository;

    @InjectMocks
    private ToggleService sut;

    @BeforeEach
    void setUp() {
        initMocks(this);
    }

    @Test
    void create_whenToggleDoesNotExist_shouldCreateAToggle() {
        when(toggleRepository.findByName(any())).thenReturn(Optional.empty());

        when(toggleRepository.upsert(testToggle())).thenReturn(testToggle());

        FeatureToggle result = sut.create(TOGGLE_NAME, TOGGLE_VALUE);

        assertThat(result).isEqualTo(testToggle());
    }

    @Test
    void create_whenToggleNameIsEmpty_shouldFail() {
        assertThatThrownBy(() -> sut.create("", TOGGLE_VALUE))
                .isInstanceOf(InvalidFeatureToggleName.class);

        verifyZeroInteractions(toggleRepository);
    }

    @Test
    void create_whenToggleNameHasSpaces_shouldFail() {
        assertThatThrownBy(() -> sut.create("TOGGLE NAME", TOGGLE_VALUE))
                .isInstanceOf(InvalidFeatureToggleName.class);

        verifyZeroInteractions(toggleRepository);
    }

    @Test
    void create_whenToggleAlreadyExist_shouldFail() {
        when(toggleRepository.findByName(new Name(TOGGLE_NAME))).thenReturn(Optional.of(testToggle()));

        assertThatThrownBy(() -> sut.create(TOGGLE_NAME, TOGGLE_VALUE))
                .isInstanceOf(FeatureToggleAlreadyExist.class);

        verify(toggleRepository, times(0)).upsert(any());
    }

    @Test
    void findAll_shouldReturnTheList() {
        when(toggleRepository.findAll()).thenReturn(List.of(testToggle()));

        List<FeatureToggle> got = sut.getAll();

        assertThat(got).containsOnly(testToggle());
    }

    @Test
    void findAll_whenNoToggles_shouldFail() {
        when(toggleRepository.findAll()).thenReturn(emptyList());

        assertThatThrownBy(() -> sut.getAll()).isInstanceOf(FeatureTogglesNotFound.class);
    }

    @Test
    void findByName_whenToggleExist_shouldReturnTheToggle() {
        when(toggleRepository.findByName(new Name(TOGGLE_NAME))).thenReturn(Optional.of(testToggle()));

        FeatureToggle got = sut.findByName(testToggle().getName());

        assertThat(got).isEqualTo(testToggle());
    }

    @Test
    void findByName_whenToggleNotExist_shouldFail() {
        when(toggleRepository.findByName(new Name(TOGGLE_NAME))).thenReturn(Optional.empty());

        assertThatThrownBy(() -> sut.findByName(TOGGLE_NAME))
                .isInstanceOf(FeatureToggleNotFound.class);
    }

    @Test
    void update_whenToggleExist_shouldUpdateTheToggle() {
        FeatureToggle expectedToggle = customToggle().withName(TOGGLE_NAME).withValue(false).build();
        when(toggleRepository.findByName(new Name(TOGGLE_NAME))).thenReturn(Optional.of(testToggle()));
        when(toggleRepository.upsert(expectedToggle)).thenReturn(expectedToggle);

        FeatureToggle result = sut.update(TOGGLE_NAME, false);

        assertThat(result).isEqualTo(expectedToggle);
    }

    @Test
    void update_whenToggleNotExist_shouldFail() {
        when(toggleRepository.findByName(new Name(TOGGLE_NAME))).thenReturn(Optional.empty());

        assertThatThrownBy(() -> sut.update(TOGGLE_NAME, TOGGLE_VALUE))
                .isInstanceOf(FeatureToggleNotFound.class);

        verify(toggleRepository, never()).upsert(testToggle());
    }

    @Test
    void delete_whenToggleNotExist_shouldFail() {
        when(toggleRepository.findByName(new Name(TOGGLE_NAME))).thenReturn(Optional.empty());

        assertThatThrownBy(() -> sut.delete(TOGGLE_NAME))
                .isInstanceOf(FeatureToggleNotFound.class);
    }

    @Test
    void delete_whenToggleExist_shouldDeleteIt() {
        when(toggleRepository.findByName(new Name(TOGGLE_NAME))).thenReturn(Optional.of(testToggle()));

        sut.delete(TOGGLE_NAME);

        verify(toggleRepository).delete(new Name(TOGGLE_NAME));
    }
}