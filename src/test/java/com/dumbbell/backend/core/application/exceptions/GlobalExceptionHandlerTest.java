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

    @Test
    void invalidExerciseName_shouldHandleInvalidName() {
        ResponseEntity<ErrorDetails> result = sut.invalidExerciseName();

        assertThat(result.getStatusCodeValue()).isEqualTo(422);
        assertThat(result.getBody().message).isEqualTo("Invalid name for the exercise");
    }

    @Test
    void invalidExerciseDescription_shouldHandleInvalidDescription() {
        ResponseEntity<ErrorDetails> result = sut.invalidExerciseDescription();

        assertThat(result.getStatusCodeValue()).isEqualTo(422);
        assertThat(result.getBody().message).isEqualTo("Invalid description for the exercise");
    }

    @Test
    void invalidExerciseDifficulty_shouldHandleInvalidDifficulty() {
        ResponseEntity<ErrorDetails> result = sut.invalidExerciseDifficulty();

        assertThat(result.getStatusCodeValue()).isEqualTo(422);
        assertThat(result.getBody().message).isEqualTo("Invalid difficulty for the exercise. Should be between 1 and 10.");
    }

    @Test
    void exerciseNotFound_shouldHandleExerciseNotFound() {
        ResponseEntity<ErrorDetails> result = sut.exerciseNotFound();

        assertThat(result.getStatusCodeValue()).isEqualTo(404);
        assertThat(result.getBody().message).isEqualTo("Exercise not found");
    }

    @Test
    void exercisesNotFound_shouldHandleExercisesNotFound() {
        ResponseEntity<ErrorDetails> result = sut.exercisesNotFound();

        assertThat(result.getStatusCodeValue()).isEqualTo(404);
        assertThat(result.getBody().message).isEqualTo("Exercises not found");
    }

    @Test
    void notEnoughPermissions_shouldHandleExercisesNotFound() {
        ResponseEntity<ErrorDetails> result = sut.notEnoughPermissions();

        assertThat(result.getStatusCodeValue()).isEqualTo(403);
        assertThat(result.getBody().message).isEqualTo("You don't have enough permissions");
    }

    @Test
    void invalidToken_shouldInvalidToken() {
        ResponseEntity<ErrorDetails> result = sut.invalidToken();

        assertThat(result.getStatusCodeValue()).isEqualTo(401);
        assertThat(result.getBody().message).isEqualTo("Invalid token");
    }

    @Test
    void accountNotFound_shouldAccountNotFound() {
        ResponseEntity<ErrorDetails> result = sut.accountNotFound();

        assertThat(result.getStatusCodeValue()).isEqualTo(404);
        assertThat(result.getBody().message).isEqualTo("User not found");
    }
}