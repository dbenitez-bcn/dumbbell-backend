package com.dumbbell.backend.toggles.presentation.controllers;

import com.dumbbell.backend.toggles.application.ToggleService;
import com.dumbbell.backend.toggles.domain.exceptions.FeatureToggleNotFound;
import com.dumbbell.backend.toggles.presentation.request.ToggleCreationRequest;
import com.dumbbell.backend.toggles.presentation.responses.ToggleResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static com.dumbbell.backend.toggles.FeatureToggleFixture.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

class ToggleControllerTest {
    @Mock
    ToggleService toggleService;

    @InjectMocks
    ToggleController sut;

    @BeforeEach
    void setUp() {
        initMocks(this);
    }

    @Test
    void create_shouldCreateAToggle() {
        when(toggleService.create(TOGGLE_NAME, TOGGLE_VALUE)).thenReturn(testToggle());

        ResponseEntity<ToggleResponse> result = sut.create(new ToggleCreationRequest(TOGGLE_NAME, TOGGLE_VALUE));

        assertThat(result.getBody()).isEqualTo(new ToggleResponse(TOGGLE_NAME, TOGGLE_VALUE));
    }

    @Test
    void findAll_shouldReturnListOfToggles() {
        when(toggleService.getAll()).thenReturn(List.of(testToggle()));

        ResponseEntity<List<ToggleResponse>> got = sut.getAll();

        assertThat(got.getBody()).containsOnly(new ToggleResponse(TOGGLE_NAME, TOGGLE_VALUE));
    }

    @ParameterizedTest
    @ValueSource(booleans = {true, false})
    void getToggleValue_whenToggleExist_shouldReturnToggleValue(boolean value) {
        when(toggleService.findByName(TOGGLE_NAME))
                .thenReturn(customToggle().withValue(value).build());

        ResponseEntity<Boolean> got = sut.getToggleValue(TOGGLE_NAME);

        assertThat(got.getBody()).isEqualTo(value);
    }

    @Test
    void getToggleValue_whenToggleNotExist_shouldReturnFalse() {
        when(toggleService.findByName(anyString())).thenThrow(FeatureToggleNotFound.class);

        ResponseEntity<Boolean> got = sut.getToggleValue(TOGGLE_NAME);

        assertThat(got.getBody()).isFalse();
    }

    @Test
    void delete_whenToggleExist_shouldDeleteTheToggle() {
        sut.deleteToggle(TOGGLE_NAME);

        verify(toggleService).delete(TOGGLE_NAME);
    }

    @Test
    void delete_whenToggleNotExist_shouldDeleteTheToggle() {
        when(toggleService.findByName(anyString())).thenThrow(FeatureToggleNotFound.class);

        sut.deleteToggle(TOGGLE_NAME);

        verify(toggleService).delete(TOGGLE_NAME);
    }
}