package com.dumbbell.backend.core.presentation.exceptions;

import com.dumbbell.backend.accounts.domain.exceptions.*;
import com.dumbbell.backend.exercises.domain.exceptions.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(EmailAlreadyInUse.class)
    public ResponseEntity<ErrorDetails> emailAlreadyInUse() {
        ErrorDetails errorDetails = new ErrorDetails("Email is already in use");
        return ResponseEntity.status(422).body(errorDetails);
    }

    @ExceptionHandler(InvalidEmailAddress.class)
    public ResponseEntity<ErrorDetails> invalidEmailAddress() {
        ErrorDetails errorDetails = new ErrorDetails("Invalid email address");
        return ResponseEntity.status(422).body(errorDetails);
    }

    @ExceptionHandler(InvalidPasswordFormat.class)
    public ResponseEntity<ErrorDetails> invalidPasswordFormat(InvalidPasswordFormat exception) {
        ErrorDetails errorDetails = new ErrorDetails("Invalid password format: " + exception.getMessage());
        return ResponseEntity.status(422).body(errorDetails);
    }

    @ExceptionHandler(LoginFailed.class)
    public ResponseEntity<ErrorDetails> loginFailed() {
        ErrorDetails errorDetails = new ErrorDetails("Invalid email or password");
        return ResponseEntity.status(403).body(errorDetails);
    }

    @ExceptionHandler(InvalidName.class)
    public ResponseEntity<ErrorDetails> invalidExerciseName() {
        ErrorDetails errorDetails = new ErrorDetails("Invalid name for the exercise");
        return ResponseEntity.status(422).body(errorDetails);
    }

    @ExceptionHandler(InvalidDescription.class)
    public ResponseEntity<ErrorDetails> invalidExerciseDescription() {
        ErrorDetails errorDetails = new ErrorDetails("Invalid description for the exercise");
        return ResponseEntity.status(422).body(errorDetails);
    }

    @ExceptionHandler(InvalidDifficulty.class)
    public ResponseEntity<ErrorDetails> invalidExerciseDifficulty() {
        ErrorDetails errorDetails = new ErrorDetails("Invalid difficulty for the exercise. Should be between 1 and 10.");
        return ResponseEntity.status(422).body(errorDetails);
    }

    @ExceptionHandler(ExerciseNotFound.class)
    public ResponseEntity<ErrorDetails> exerciseNotFound() {
        ErrorDetails errorDetails = new ErrorDetails("Exercise not found");
        return ResponseEntity.status(404).body(errorDetails);
    }

    @ExceptionHandler(ExercisesNotFound.class)
    public ResponseEntity<ErrorDetails> exercisesNotFound() {
        ErrorDetails errorDetails = new ErrorDetails("Exercises not found");
        return ResponseEntity.status(404).body(errorDetails);
    }

    @ExceptionHandler(NotEnoughPermissions.class)
    public ResponseEntity<ErrorDetails> notEnoughPermissions() {
        ErrorDetails errorDetails = new ErrorDetails("You don't have enough permissions");
        return ResponseEntity.status(403).body(errorDetails);
    }
}
