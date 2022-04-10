package com.dumbbell.backend.toggles.presentation.controllers;

import com.dumbbell.backend.toggles.application.ToggleService;
import com.dumbbell.backend.toggles.presentation.request.ToggleCreationRequest;
import com.dumbbell.backend.toggles.presentation.responses.ToggleResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static com.dumbbell.backend.toggles.FeatureToggleFixture.*;
import static org.assertj.core.api.Assertions.assertThat;
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

        assertThat(result.getBody().name).isEqualTo(TOGGLE_NAME);
        assertThat(result.getBody().value).isEqualTo(TOGGLE_VALUE);
    }

    @Test
    void findAll_shouldReturnListOfToggles() {
        when(toggleService.getAll()).thenReturn(List.of(testToggle()));

        ResponseEntity<List<ToggleResponse>> got = sut.getAll();

        assertThat(got.getBody()).containsOnly(new ToggleResponse(TOGGLE_NAME, TOGGLE_VALUE));
    }
}