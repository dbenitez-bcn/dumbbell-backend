package com.dumbbell.backend.accounts.domain.valueObjects;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class HashedPassword {
    private final String value;
}
