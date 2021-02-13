package com.dumbbell.backend.toggles.presentation.controllers;

import com.dumbbell.backend.toggles.application.ToggleService;
import com.dumbbell.backend.toggles.domain.aggregates.FeatureToggle;
import com.dumbbell.backend.toggles.presentation.request.ToggleCreationRequest;
import com.dumbbell.backend.toggles.presentation.responses.ToggleResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.ResponseEntity;

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
        FeatureToggle testToggle = testToggle();
        when(toggleService.create(TOGGLE_NAME, TOGGLE_VALUE)).thenReturn(testToggle);

        ResponseEntity<ToggleResponse> result = sut.create(new ToggleCreationRequest(TOGGLE_NAME, TOGGLE_VALUE));

        assertThat(result.getBody().name).isEqualTo(TOGGLE_NAME);
        assertThat(result.getBody().value).isEqualTo(TOGGLE_VALUE);
    }
}