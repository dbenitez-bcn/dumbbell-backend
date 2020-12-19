package com.dumbbell.backend.accounts.fixtures;


import com.dumbbell.backend.accounts.domain.aggregates.Account;

public class AccountFixture {
    public static String ACCOUNT_EMAIL = "example@biblioteca.com";
    public static String ACCOUNT_PASSWORD = "password1234";
    public static String ENCODED_PASSWORD = "encodedPassword";

    public static Account customAccount(String email, String password) {
        return new Account(email, password);
    }

    public static Account defaultAccount() {
        return new Account(ACCOUNT_EMAIL, ENCODED_PASSWORD);
    }
}
