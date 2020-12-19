package com.dumbbell.backend.accounts.application;

import com.dumbbell.backend.accounts.domain.valueObjects.HashedPassword;
import com.dumbbell.backend.accounts.domain.valueObjects.PlainPassword;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static com.dumbbell.backend.accounts.fixtures.AccountFixture.ACCOUNT_PASSWORD;
import static com.dumbbell.backend.accounts.fixtures.AccountFixture.ENCODED_PASSWORD;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

class PasswordManagerTest {
    @Mock
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @InjectMocks
    private PasswordManager sut;

    @BeforeEach
    void setUp() {
        initMocks(this);
    }

    @Test
    void encode_shouldEncodeTheRawPassword() {
        PlainPassword plainPassword = new PlainPassword(ACCOUNT_PASSWORD);
        when(bCryptPasswordEncoder.encode(ACCOUNT_PASSWORD)).thenReturn(ENCODED_PASSWORD);

        String result = sut.encode(plainPassword);

        assertThat(result).isEqualTo(ENCODED_PASSWORD);
    }

    @ParameterizedTest
    @ValueSource(booleans = {true, false})
    void matches_shouldReturnIfBothPasswordsMatch(boolean value) {
        PlainPassword plainPassword = new PlainPassword(ACCOUNT_PASSWORD);
        HashedPassword hashedPassword = new HashedPassword(ACCOUNT_PASSWORD);
        when(bCryptPasswordEncoder.matches(ACCOUNT_PASSWORD, ACCOUNT_PASSWORD)).thenReturn(value);

        boolean result = sut.matches(plainPassword, hashedPassword);

        assertThat(result).isEqualTo(value);
    }
}