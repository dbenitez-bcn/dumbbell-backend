package com.dumbbell.backend.accounts.domain.repositories;

import com.dumbbell.backend.accounts.domain.aggregates.Account;
import com.dumbbell.backend.accounts.domain.valueObjects.AccountEmail;

import java.util.Optional;

public interface AccountRepository {
    void create(Account account);
    Optional<Account> getByEmail(AccountEmail email);
}
