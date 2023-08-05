package com.dumbbell.backend.accounts;

import com.dumbbell.backend.accounts.infrastructure.postgresql.entities.EmailIdentityEntity;

import java.util.UUID;

public class EmailIdentityEntityFixture {
    public static UUID ACCOUNT_ENTRY_ID = UUID.randomUUID();
    public static String ACCOUNT_ENTRY_EMAIL = "example@biblioteca.com";
    public static String ACCOUNT_USER_PASSWORD = "PASSWORD";

    public static EmailIdentityEntity customEmailIdentityEntity(UUID id, String email, String password) {
        return new EmailIdentityEntity(id, email, password);
    }

    public static EmailIdentityEntity defaultEmailIdentityEntity() {
        return new EmailIdentityEntity(ACCOUNT_ENTRY_ID, ACCOUNT_ENTRY_EMAIL, ACCOUNT_USER_PASSWORD);
    }
}
