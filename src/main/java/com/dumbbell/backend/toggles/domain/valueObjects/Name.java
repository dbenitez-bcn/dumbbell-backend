package com.dumbbell.backend.toggles.domain.valueObjects;

import com.dumbbell.backend.toggles.domain.exceptions.InvalidFeatureToggleName;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode
public class Name {
    private final String value;

    public Name(String value) {
        if (value.isEmpty() || value.contains(" ")) throw new InvalidFeatureToggleName();
        this.value = value;
    }
}
