package com.dumbbell.backend.toggles.presentation.controllers;

import com.dumbbell.backend.toggles.application.ToggleService;
import com.dumbbell.backend.toggles.domain.aggregates.FeatureToggle;
import com.dumbbell.backend.toggles.presentation.request.ToggleCreationRequest;
import com.dumbbell.backend.toggles.presentation.responses.ToggleResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RestController
@RequestMapping("toggle")
public class ToggleController {
    private final ToggleService toggleService;

    @PostMapping()
    public ResponseEntity<ToggleResponse> create(@RequestBody ToggleCreationRequest request) {
        FeatureToggle toggle = toggleService.create(request.name, request.value);

        return ResponseEntity.ok(toResponse(toggle));
    }

    @GetMapping()
    public ResponseEntity<List<ToggleResponse>> getAll() {
        List<ToggleResponse> all = toggleService
                .getAll()
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
        return ResponseEntity.ok(all);
    }

    @GetMapping("/{name}")
    public ResponseEntity<Boolean> getToggleValue(@PathVariable("name") String name) {
        try {
            return ResponseEntity.ok(toggleService.findByName(name).getValue());
        } catch (Exception e) {
            return ResponseEntity.ok(false);
        }
    }

    @DeleteMapping("/{name}")
    public ResponseEntity<Boolean> deleteToggle(@PathVariable("name") String name) {
        try {
            toggleService.delete(name);
        } catch (Exception ignored) {}

        return ResponseEntity.noContent().build();
    }

    private ToggleResponse toResponse(FeatureToggle toggle) {
        return new ToggleResponse(toggle.getName(), toggle.getValue());
    }

}
