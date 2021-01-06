package com.dumbbell.backend.exercises.infrastructure.postgresql.implementations;

import com.dumbbell.backend.exercises.infrastructure.postgresql.entities.ExerciseEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExerciseDataSource extends JpaRepository<ExerciseEntity, Integer> {
}
