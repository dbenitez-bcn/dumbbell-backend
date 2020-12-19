package com.dumbbell.backend.accounts.domain.valueObjects;

import com.dumbbell.backend.accounts.domain.exceptions.InvalidPasswordFormat;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode
public class PlainPassword {
    private final String value;

    public PlainPassword(String value) {
        if (value == null || !isPasswordValid(value)) {
            throw new InvalidPasswordFormat();
        }
        this.value = value;
    }

    private boolean isPasswordValid(String password) {
        return password.matches("^(?=.*[0-9])(?=.*[a-zA-Z]).{8,}$");
    }
}
