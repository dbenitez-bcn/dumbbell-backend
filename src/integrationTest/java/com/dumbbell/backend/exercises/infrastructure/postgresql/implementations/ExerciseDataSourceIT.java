package com.dumbbell.backend.exercises.infrastructure.postgresql.implementations;

import com.dumbbell.backend.exercises.infrastructure.postgresql.entities.ExerciseEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static com.dumbbell.backend.exercises.ExerciseEntityFixture.MUSCLE_UP_ENTITY;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@ActiveProfiles("test")
public class ExerciseDataSourceIT {

    @Autowired
    private ExerciseDataSource sut;

    @Test
    void save_shouldSaveTheEntity() {
        ExerciseEntity entity = MUSCLE_UP_ENTITY;

        ExerciseEntity result = sut.save(entity);

        assertThat(result).isEqualTo(entity);
    }
}
