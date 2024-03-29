package com.dumbbell.backend.toggles.domain.aggregates;

import com.dumbbell.backend.toggles.domain.valueObjects.Name;
import com.dumbbell.backend.toggles.domain.valueObjects.Value;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode
public class FeatureToggle {
    private Name name;
    private Value value;

    public FeatureToggle(String name, boolean value) {
        this.name = new Name(name);
        this.value = new Value(value);
    }

    public String getName() {
        return name.getValue();
    }

    public boolean getValue() {
        return this.value.isValue();
    }

    public void setValue(boolean value) {
        this.value = new Value(value);
    }
}
