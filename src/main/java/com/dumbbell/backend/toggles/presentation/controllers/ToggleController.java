package com.dumbbell.backend.toggles.presentation.controllers;

import com.dumbbell.backend.toggles.application.ToggleService;
import com.dumbbell.backend.toggles.domain.aggregates.FeatureToggle;
import com.dumbbell.backend.toggles.presentation.request.ToggleCreationRequest;
import com.dumbbell.backend.toggles.presentation.responses.ToggleResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;

@RequiredArgsConstructor
public class ToggleController {
    private final ToggleService toggleService;

    public ResponseEntity<ToggleResponse> create(ToggleCreationRequest request) {
        FeatureToggle toggle = toggleService.create(request.name, request.value);

        return ResponseEntity.ok(new ToggleResponse(toggle.getName(), toggle.getValue()));
    }
}
