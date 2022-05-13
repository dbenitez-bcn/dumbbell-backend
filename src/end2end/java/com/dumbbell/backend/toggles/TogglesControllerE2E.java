package com.dumbbell.backend.toggles;

import com.dumbbell.backend.utils.ApplicationTestCase;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

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
    void givenAUser_whenTheyCheckAToggleValue_thenTheyShouldReceiveIt() throws Exception {
        endpointRequest()
                .get("/toggle/TEST_TOGGLE")
                .authorization(createUserToken())
                .thenAssert()
                .withCode(200);
    }

    @Test
    void givenAUser_whenCheckAllToggles_thenTheyGetAForbiddenError() throws Exception {
        endpointRequest()
                .get("/toggle")
                .authorization(createUserToken())
                .thenAssert()
                .withCode(403);
    }

    @Test
    void givenAnOperator_whenCheckAllToggles_thenTheyShouldSeeAllToggles() throws Exception {
        endpointRequest()
                .get("/toggle")
                .authorization(createOperatorToken())
                .thenAssert()
                .withCode(200);
    }

    @Test
    void shouldFailWhenNoTogglesExist() throws Exception {
        Object result = endpointRequest()
                .get("/toggle")
                .authorization(createAdminToken())
                .thenAssert()
                .withCode(404)
                .getResponseBody()
                .get("message");

        assertThat(result).isEqualTo("Toggles not found");
    }

    @Test
    void toggleCreationFlowTest() throws Exception {
        String toggleName = "TOGGLE_NAME";
        String token = createAdminToken();
        JSONObject badBody = new JSONObject();
        badBody.put("name", "bad name");
        badBody.put("value", true);

        endpointRequest()
                .get("/toggle/" + toggleName)
                .authorization(token)
                .thenAssert()
                .withCode(200)
                .withResponse("false");

        endpointRequest()
                .post("/toggle")
                .authorization(token)
                .body(badBody)
                .thenAssert()
                .withCode(422)
                .withMessage("Invalid toggle name. Shouldn't have spaces or be empty");

        JSONObject rightBody = new JSONObject();
        rightBody.put("name", toggleName);
        rightBody.put("value", true);

        endpointRequest()
                .post("/toggle")
                .authorization(token)
                .body(rightBody)
                .thenAssert()
                .withCode(200);

        endpointRequest()
                .get("/toggle/" + toggleName)
                .authorization(token)
                .thenAssert()
                .withCode(200)
                .withResponse("true");

        endpointRequest()
                .post("/toggle")
                .authorization(token)
                .body(rightBody)
                .thenAssert()
                .withCode(422)
                .withMessage("Toggle already exist");

        endpointRequest()
                .get("/toggle")
                .authorization(token)
                .thenAssert()
                .withCode(200);

    }
}
