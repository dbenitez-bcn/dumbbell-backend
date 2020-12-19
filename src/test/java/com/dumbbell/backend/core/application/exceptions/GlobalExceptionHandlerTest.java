package com.dumbbell.backend.core.application.exceptions;

import com.dumbbell.backend.accounts.domain.exceptions.InvalidPasswordFormat;
import com.dumbbell.backend.core.presentation.exceptions.ErrorDetails;
import com.dumbbell.backend.core.presentation.exceptions.GlobalExceptionHandler;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;

class GlobalExceptionHandlerTest {

    private final GlobalExceptionHandler sut = new GlobalExceptionHandler();

    @Test
    void emailAlreadyInUse_shouldHandleEmailAlreadyInUse() {
        ResponseEntity<ErrorDetails> result = sut.emailAlreadyInUse();

        assertThat(result.getStatusCodeValue()).isEqualTo(422);
        assertThat(result.getBody().message).isEqualTo("Email is already in use");
    }

    @Test
    void invalidEmailAddress_shouldHandleInvalidEmailAddress() {
        ResponseEntity<ErrorDetails> result = sut.invalidEmailAddress();

        assertThat(result.getStatusCodeValue()).isEqualTo(422);
        assertThat(result.getBody().message).isEqualTo("Invalid email address");
    }

    @Test
    void invalidPasswordFormat_shouldHandleInvalidPasswordFormat() {
        InvalidPasswordFormat exception = new InvalidPasswordFormat();

        ResponseEntity<ErrorDetails> result = sut.invalidPasswordFormat(exception);

        assertThat(result.getStatusCodeValue()).isEqualTo(422);
        assertThat(result.getBody().message).isEqualTo("Invalid password format: " + exception.getMessage());
    }

    @Test
    void loginFailed_shouldHandleLoginFailed() {
        ResponseEntity<ErrorDetails> result = sut.loginFailed();

        assertThat(result.getStatusCodeValue()).isEqualTo(403);
        assertThat(result.getBody().message).isEqualTo("Invalid email or password");
    }
}