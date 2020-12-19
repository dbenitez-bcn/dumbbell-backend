package com.dumbbell.backend.accounts.fixtures;

import com.dumbbell.backend.accounts.infrastructure.postgresql.entities.AccountEntity;

import java.util.UUID;

public class AccountEntityFixture {
    public static UUID ACCOUNT_ENTRY_ID = UUID.randomUUID();
    public static String ACCOUNT_ENTRY_EMAIL = "example@biblioteca.com";
    public static String ENCODED_ENTRY_PASSWORD = "encodedPassword";
    public static String ACCOUNT_USER_ROLE = "USER";
    public static String ACCOUNT_ADMIN_ROLE = "ADMIN";

    public static AccountEntity customAccountEntity(UUID id, String email, String password, String role) {
        return new AccountEntity(id, email, password, role);
    }

    public static AccountEntity defaultAccountEntity() {
        return new AccountEntity(ACCOUNT_ENTRY_ID, ACCOUNT_ENTRY_EMAIL, ENCODED_ENTRY_PASSWORD, ACCOUNT_USER_ROLE);
    }
}
