package com.dumbbell.backend.accounts.presentation.controllers;

import com.dumbbell.backend.accounts.utils.ApplicationTestCase;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;

import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpMethod.POST;

class AccountControllerE2E extends ApplicationTestCase {
    @Test
    void whenUserIntroducesWrongCredentialsShouldFail() throws Exception {
        JSONObject body = new JSONObject();
        body.put("email", "fake@biblioteca.com");
        body.put("password", "wrongPassword1234");

        assertRequestWithBody(GET, "/login", body, 403);
    }

    @Test
    void afterUserCreatesANewAccountShouldBeAbleToLogIn() throws Exception {
        JSONObject body = new JSONObject();
        body.put("email", "testerino@biblioteca.com");
        body.put("password", "password1234");

        assertRequestWithBody(POST, "/register", body, 200);
        assertRequestWithBody(POST, "/login", body, 200);
    }
}