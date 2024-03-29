package com.dumbbell.backend.accounts.infrastructure.postgresql.implementations;

import com.dumbbell.backend.accounts.domain.aggregates.Account;
import com.dumbbell.backend.accounts.domain.repositories.AccountRepository;
import com.dumbbell.backend.accounts.domain.valueObjects.AccountEmail;
import com.dumbbell.backend.accounts.infrastructure.postgresql.entities.AccountEntity;
import com.dumbbell.backend.accounts.infrastructure.postgresql.entities.EmailIdentityEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class PostgresAccountRepository implements AccountRepository {
    private final AccountDataSource accountDataSource;

    @Override
    public void create(Account account) {
        AccountEntity accountEntity = new AccountEntity(
                account.getId(),
                account.getEmail().getValue(),
                account.getRole().name(),
                new EmailIdentityEntity(
                        UUID.randomUUID(),
                        account.getEmail().getValue(),
                        account.getPassword().getValue()
                )
        );
        accountDataSource.save(accountEntity);
    }

    @Override
    public Optional<Account> getByEmail(AccountEmail email) {
        return accountDataSource
                .findByEmail(email.getValue())
                .map(this::mapToAccount);
    }

    @Override
    public Optional<Account> getById(UUID id) {
        return accountDataSource
                .findById(id)
                .map(this::mapToAccount);
    }

    private Account mapToAccount(AccountEntity entity) {
        return new Account(
                entity.getId(),
                entity.getEmail(),
                entity.getEmailIdentityEntity().getPassword(),
                entity.getRole()
        );
    }
}
