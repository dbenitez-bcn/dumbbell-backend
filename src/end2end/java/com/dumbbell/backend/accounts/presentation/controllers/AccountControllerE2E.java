package com.dumbbell.backend.accounts.presentation.controllers;

import com.dumbbell.backend.utils.ApplicationTestCase;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;

class AccountControllerE2E extends ApplicationTestCase {
    @Test
    void whenUserIntroducesWrongCredentialsShouldFail() throws Exception {
        JSONObject body = new JSONObject();
        body.put("email", "fake@biblioteca.com");
        body.put("password", "wrongPassword1234");
        JSONObject expectedResponse = new JSONObject();
        expectedResponse.put("message", "Invalid email or password");

        endpointRequest()
                .post("/login")
                .body(body)
                .thenAssert()
                .withCode(403)
                .withResponse(expectedResponse);
    }

    @Test
    void afterUserCreatesANewAccountShouldBeAbleToLogIn() throws Exception {
        JSONObject body = new JSONObject();
        body.put("email", "testerino@biblioteca.com");
        body.put("password", "password1234");

        endpointRequest()
                .post("/register")
                .body(body)
                .thenAssert()
                .withCode(201);

        endpointRequest()
                .post("/login")
                .body(body)
                .thenAssert()
                .withCode(200);
    }
}