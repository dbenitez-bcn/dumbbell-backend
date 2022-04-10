package com.dumbbell.backend.toggles.presentation.responses;

import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@EqualsAndHashCode
public class ToggleResponse {
    public final String name;
    public final boolean value;
}
