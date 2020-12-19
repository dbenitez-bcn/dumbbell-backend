package com.dumbbell.backend.accounts.infrastructure.postgresql;

import com.dumbbell.backend.accounts.infrastructure.postgresql.entities.AccountEntity;
import com.dumbbell.backend.accounts.infrastructure.postgresql.implementations.AccountDataSource;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static com.dumbbell.backend.accounts.fixtures.AccountEntityFixture.defaultAccountEntity;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@ActiveProfiles("test")
class AccountDataSourceIT {
    @Autowired
    private AccountDataSource sut;

    @Test
    void findByEmail_whenAccountExist_shouldReturnAnAccount() {
        AccountEntity entity = defaultAccountEntity();
        sut.save(entity);

        Optional<AccountEntity> result = sut.findByEmail(entity.getEmail());

        assertThat(result).contains(entity);
    }

    @Test
    void findByEmail_whenAccountDontExist_shouldReturnNothing() {
        Optional<AccountEntity> result = sut.findByEmail("notRegistered@email.com");

        assertThat(result).isEmpty();
    }
}