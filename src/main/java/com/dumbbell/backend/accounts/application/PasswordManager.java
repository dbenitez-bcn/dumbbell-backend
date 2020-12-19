package com.dumbbell.backend.accounts.application;

import com.dumbbell.backend.accounts.domain.valueObjects.HashedPassword;
import com.dumbbell.backend.accounts.domain.valueObjects.PlainPassword;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
class PasswordManager {

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public String encode(PlainPassword plainPassword) {
        return bCryptPasswordEncoder.encode(plainPassword.getValue());
    }

    public boolean matches(PlainPassword plainPassword, HashedPassword hashedPassword) {
        return bCryptPasswordEncoder.matches(plainPassword.getValue(), hashedPassword.getValue());
    }
}
