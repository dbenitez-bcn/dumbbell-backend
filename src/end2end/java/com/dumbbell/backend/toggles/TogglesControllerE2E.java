package com.dumbbell.backend.toggles;

import com.dumbbell.backend.utils.ApplicationTestCase;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;

public class TogglesControllerE2E extends ApplicationTestCase {

    @Test
    void givenNonAdminUsers_whenTheyCreateAToggle_thenTheyGetAForbiddenError() throws Exception {
        endpointRequest()
                .post("/toggle")
                .authorization(createOperatorToken())
                .thenAssert()
                .withCode(403);
    }

    @Test
    void toggleCreationFlowTest() throws Exception {
        String token = createAdminToken();
        JSONObject badBody = new JSONObject();
        badBody.put("name", "bad name");
        badBody.put("value", true);

        endpointRequest()
                .post("/toggle")
                .authorization(token)
                .body(badBody)
                .thenAssert()
                .withCode(422)
                .withMessage("Invalid toggle name. Shouldn't have spaces or be empty");

        JSONObject rightBody = new JSONObject();
        rightBody.put("name", "TOGGLE_NAME");
        rightBody.put("value", true);

        endpointRequest()
                .post("/toggle")
                .authorization(token)
                .body(rightBody)
                .thenAssert()
                .withCode(200);

        endpointRequest()
                .post("/toggle")
                .authorization(token)
                .body(rightBody)
                .thenAssert()
                .withCode(422)
                .withMessage("Toggle already exist");
    }
}
