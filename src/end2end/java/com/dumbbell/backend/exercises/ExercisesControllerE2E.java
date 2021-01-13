package com.dumbbell.backend.exercises;

import com.dumbbell.backend.utils.ApplicationTestCase;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpMethod;

public class ExercisesControllerE2E extends ApplicationTestCase {

    @Test
    void shouldFailWhenNoExercisesExist() throws Exception {
        JSONObject response = new JSONObject();
        response.put("message", "Exercises not found");
        assertRequest(HttpMethod.GET, "/exercises")
                .withCode(404)
                .withResponse(response);

        response.put("message", "Exercise not found");
        assertRequest(HttpMethod.GET, "/exercise/1234")
                .withCode(404)
                .withResponse(response);
    }
}
