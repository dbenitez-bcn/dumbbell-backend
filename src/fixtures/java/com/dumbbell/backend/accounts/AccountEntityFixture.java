package com.dumbbell.backend.accounts;

import com.dumbbell.backend.accounts.infrastructure.postgresql.entities.AccountEntity;
import com.dumbbell.backend.accounts.infrastructure.postgresql.entities.EmailIdentityEntity;

import java.util.UUID;

import static com.dumbbell.backend.accounts.EmailIdentityEntityFixture.defaultEmailIdentityEntity;

public class AccountEntityFixture {
    public static UUID ACCOUNT_ENTRY_ID = UUID.randomUUID();
    public static String ACCOUNT_ENTRY_EMAIL = "example@biblioteca.com";
    public static String ENCODED_ENTRY_PASSWORD = "encodedPassword";
    public static String ACCOUNT_USER_ROLE = "USER";
    public static String ACCOUNT_ADMIN_ROLE = "ADMIN";

    public static AccountEntity customAccountEntity(UUID id, String email, String password, String role, EmailIdentityEntity emailIdentityEntity) {
        return new AccountEntity(id, email, password, role, emailIdentityEntity);
    }

    public static AccountEntity defaultAccountEntity() {
        return new AccountEntity(ACCOUNT_ENTRY_ID, ACCOUNT_ENTRY_EMAIL, ENCODED_ENTRY_PASSWORD, ACCOUNT_USER_ROLE, defaultEmailIdentityEntity());
    }
}
