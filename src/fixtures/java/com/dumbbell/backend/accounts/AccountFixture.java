package com.dumbbell.backend.accounts;


import com.dumbbell.backend.accounts.domain.aggregates.Account;

import java.util.UUID;

public class AccountFixture {
    public static String ACCOUNT_EMAIL = "example@biblioteca.com";
    public static String ACCOUNT_PASSWORD = "password1234";
    public static String ENCODED_PASSWORD = "encodedPassword";
    public static final Account ACCOUNT = customAccount(ACCOUNT_EMAIL, ACCOUNT_PASSWORD, "USER");
    public static final Account OPERATOR_ACCOUNT = customAccount(ACCOUNT_EMAIL, ACCOUNT_PASSWORD, "OPERATOR");
    public static final Account ADMIN_ACCOUNT = customAccount(ACCOUNT_EMAIL, ACCOUNT_PASSWORD, "ADMIN");

    public static Account customAccount(String email, String password, String role) {
        return new Account(UUID.randomUUID(), email, password, role);
    }

    public static Account defaultAccount() {
        return new Account(ACCOUNT_EMAIL, ENCODED_PASSWORD);
    }
}
