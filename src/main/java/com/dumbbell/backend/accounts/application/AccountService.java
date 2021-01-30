package com.dumbbell.backend.accounts.application;

import com.dumbbell.backend.accounts.domain.aggregates.Account;
import com.dumbbell.backend.accounts.domain.exceptions.EmailAlreadyInUse;
import com.dumbbell.backend.accounts.domain.exceptions.LoginFailed;
import com.dumbbell.backend.accounts.domain.exceptions.NotEnoughPermissions;
import com.dumbbell.backend.accounts.domain.repositories.AccountRepository;
import com.dumbbell.backend.accounts.domain.valueObjects.AccountEmail;
import com.dumbbell.backend.accounts.domain.valueObjects.HashedPassword;
import com.dumbbell.backend.accounts.domain.valueObjects.PlainPassword;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AccountService {

    private final AccountRepository accountRepository;
    private final PasswordManager passwordManager;

    public void register(String email, String password) {
        Optional<Account> accountMaybe = accountRepository.getByEmail(new AccountEmail(email));
        accountMaybe.ifPresent((account) -> {
            throw new EmailAlreadyInUse();
        });
        String hashedPassword = passwordManager.encode(new PlainPassword(password));
        Account account = new Account(email, hashedPassword);
        accountRepository.create(account);
    }

    public Account login(String email, String password) {
        Account account = getAccount(email);
        failIfPasswordDoNotMatch(new PlainPassword(password), account.getPassword());
        return account;
    }

    public Account operatorLogin(String email, String password) {
        Account account = getAccount(email);
        if (!account.hasOperatorPermissions()) {
            throw new NotEnoughPermissions();
        }
        failIfPasswordDoNotMatch(new PlainPassword(password), account.getPassword());
        return account;
    }

    private Account getAccount(String email) {
        return accountRepository
                .getByEmail(new AccountEmail(email))
                .orElseThrow(LoginFailed::new);
    }

    private void failIfPasswordDoNotMatch(PlainPassword plain, HashedPassword hashed) {
        if (!passwordManager.matches(plain, hashed)) {
            throw new LoginFailed();
        }
    }
}
