package com.dumbbell.backend.core.presentation.config;

import lombok.RequiredArgsConstructor;
import org.flywaydb.core.Flyway;
import org.flywaydb.core.api.configuration.FluentConfiguration;
import org.springframework.boot.autoconfigure.flyway.FlywayMigrationStrategy;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;

@Component
@RequiredArgsConstructor
public class FlywayMultiSchemaManager implements FlywayMigrationStrategy {

    private final FluentConfiguration flywayConfig;

    @Override
    public void migrate(Flyway flyway) {
        DataSource dataSource = flyway.getConfiguration().getDataSource();
        flywayConfig
                .schemas("accounts")
                .locations("db/migration/accounts")
                .dataSource(dataSource)
                .load()
                .migrate();

        flywayConfig
                .schemas("exercises")
                .locations("db/migration/exercises")
                .dataSource(dataSource)
                .load()
                .migrate();

        flywayConfig
                .schemas("toggles")
                .locations("db/migration/toggles")
                .dataSource(dataSource)
                .load()
                .migrate();
    }

}