package com.dumbbell.backend.core.presentation.config;

import org.flywaydb.core.Flyway;
import org.flywaydb.core.api.configuration.FluentConfiguration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Answers;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.initMocks;

class FlywayMultiSchemaManagerTest {
    @Mock(answer = Answers.RETURNS_DEEP_STUBS)
    private Flyway flyway;

    @Mock(answer = Answers.RETURNS_DEEP_STUBS)
    private FluentConfiguration flywayConfig;

    @InjectMocks
    private FlywayMultiSchemaManager sut;

    @BeforeEach
    void setUp() {
        initMocks(this);
    }

    @Test
    void migrate_shouldExecuteExercisesMigrations() {
        sut.migrate(flyway);

        verify(flywayConfig
                .schemas("exercises")
                .locations("db/migration/exercises")
                .dataSource(flyway.getConfiguration().getDataSource())
                .load()).migrate();
    }

    @Test
    void migrate_shouldExecuteAccountsMigrations() {
        sut.migrate(flyway);

        verify(flywayConfig
                .schemas("accounts")
                .locations("db/migration/accounts")
                .dataSource(flyway.getConfiguration().getDataSource())
                .load()).migrate();
    }

    @Test
    void migrate_shouldExecuteToggleMigrations() {
        sut.migrate(flyway);

        verify(flywayConfig
                .schemas("toggles")
                .locations("db/migration/toggles")
                .dataSource(flyway.getConfiguration().getDataSource())
                .load()).migrate();
    }

}