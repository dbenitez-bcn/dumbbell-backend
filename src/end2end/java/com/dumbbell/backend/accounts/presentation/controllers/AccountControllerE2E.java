package com.dumbbell.backend.accounts.presentation.controllers;

import com.dumbbell.backend.utils.ApplicationTestCase;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;

class AccountControllerE2E extends ApplicationTestCase {
    @Test
    void whenUserIntroducesWrongCredentialsShouldFail() throws Exception {
        JSONObject body = new JSONObject();
        body.put("email", "fake@dumbbell.com");
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
    void afterUserCreatesANewAccountShouldBeAbleToLogInButNotToAdminPanel() throws Exception {
        JSONObject body = new JSONObject();
        body.put("email", "testerino@dumbbell.com");
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

        endpointRequest()
                .post("/admin/login")
                .body(body)
                .thenAssert()
                .withCode(403);
    }

    @Test
    void aUserCanLogout() throws Exception {
        endpointRequest()
                .delete("/auth/logout")
                .thenAssert()
                .withCode(204);
    }

    @Test
    void whenAUserLogInReceivesARefreshTokenAndThenUseItToGetANewToken() throws Exception {
        JSONObject body = new JSONObject();
        body.put("email", "refresh@dumbbell.com");
        body.put("password", "password1234");

        endpointRequest()
                .post("/register")
                .body(body)
                .thenAssert()
                .withCode(201);

        JSONObject loginResponse = endpointRequest()
                .post("/login")
                .body(body)
                .thenAssert()
                .getResponseBody();

        JSONObject refreshBody = new JSONObject();
        refreshBody.put("refreshToken", loginResponse.get("refreshToken"));
        endpointRequest()
                .post("/auth/refresh")
                .body(refreshBody)
                .thenAssert()
                .withCode(200);
    }

    @Test
    void shouldNotGetANewTokenForAnInvalidRefreshToken() throws Exception {
        JSONObject body = new JSONObject();
        body.put("refreshToken", "InvalidToken");
        endpointRequest()
                .post("/auth/refresh")
                .body(body)
                .thenAssert()
                .withCode(401);
    }
}