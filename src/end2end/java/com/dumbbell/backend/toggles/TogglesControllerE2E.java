package com.dumbbell.backend.toggles;

import com.dumbbell.backend.utils.ApplicationTestCase;
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
}
