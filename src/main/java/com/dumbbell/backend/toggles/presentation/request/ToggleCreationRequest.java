package com.dumbbell.backend.toggles.presentation.request;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ToggleCreationRequest {
    public final String name;
    public final boolean value;
}
