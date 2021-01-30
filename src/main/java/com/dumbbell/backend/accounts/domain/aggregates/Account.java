package com.dumbbell.backend.accounts.domain.aggregates;

import com.dumbbell.backend.accounts.domain.valueObjects.AccountEmail;
import com.dumbbell.backend.accounts.domain.valueObjects.AccountRole;
import com.dumbbell.backend.accounts.domain.valueObjects.HashedPassword;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.UUID;


@Getter
@EqualsAndHashCode
public class Account {
    private UUID id;
    private AccountEmail email;
    private HashedPassword password;
    private AccountRole role;

    public Account(String email, String password) {
        this.id = UUID.randomUUID();
        this.email = new AccountEmail(email);
        this.password = new HashedPassword(password);
        this.role = AccountRole.USER;
    }

    public Account(UUID id, String email, String password, String role) {
        this.id = id;
        this.email = new AccountEmail(email);
        this.password = new HashedPassword(password);
        this.role = AccountRole.valueOf(role);
    }

    public boolean isOperator() {
        return role == AccountRole.OPERATOR;
    }

    public boolean isAdmin() {
        return role == AccountRole.ADMIN;
    }

    public boolean hasOperatorPermissions() {
        return isOperator() || isAdmin();
    }
}
