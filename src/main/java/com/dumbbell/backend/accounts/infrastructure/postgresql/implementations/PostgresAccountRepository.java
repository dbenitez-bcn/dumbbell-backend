package com.dumbbell.backend.accounts.infrastructure.postgresql.implementations;

import com.dumbbell.backend.accounts.domain.aggregates.Account;
import com.dumbbell.backend.accounts.domain.repositories.AccountRepository;
import com.dumbbell.backend.accounts.domain.valueObjects.AccountEmail;
import com.dumbbell.backend.accounts.infrastructure.postgresql.entities.AccountEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class PostgresAccountRepository implements AccountRepository {
    private final AccountDataSource accountDataSource;

    @Override
    public void create(Account account) {
        AccountEntity accountEntity = new AccountEntity(
                account.getId(),
                account.getEmail().getValue(),
                account.getPassword().getValue(),
                account.getRole().name()
        );
        accountDataSource.save(accountEntity);
    }

    @Override
    public Optional<Account> getByEmail(AccountEmail email) {
        Optional<AccountEntity> accountMaybe = accountDataSource.findByEmail(email.getValue());
        return accountMaybe.map(accountEntity -> new Account(
                accountMaybe.get().getId(),
                accountMaybe.get().getEmail(),
                accountMaybe.get().getPassword(),
                accountMaybe.get().getRole()
        ));
    }
}
