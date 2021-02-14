package com.dumbbell.backend.core.presentation.config;

import org.flywaydb.core.Flyway;
import org.springframework.boot.autoconfigure.flyway.FlywayMigrationStrategy;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;

@Component
public class FlywayMultiSchemaManager implements FlywayMigrationStrategy {

    @Override
    public void migrate(Flyway flyway) {
        DataSource dataSource = flyway.getConfiguration().getDataSource();
        Flyway.configure()
                .schemas("accounts")
                .locations("db/migration/accounts")
                .dataSource(dataSource)
                .load()
                .migrate();

        Flyway.configure()
                .schemas("exercises")
                .locations("db/migration/exercises")
                .dataSource(dataSource)
                .load()
                .migrate();

        Flyway.configure()
                .schemas("toggles")
                .locations("db/migration/toggles")
                .dataSource(dataSource)
                .load()
                .migrate();
    }

}