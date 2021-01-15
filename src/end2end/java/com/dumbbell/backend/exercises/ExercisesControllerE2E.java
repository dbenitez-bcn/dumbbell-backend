package com.dumbbell.backend.exercises;

import com.dumbbell.backend.core.utils.JwtUtils;
import com.dumbbell.backend.utils.ApplicationTestCase;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.UUID;

public class ExercisesControllerE2E extends ApplicationTestCase {

    @Autowired
    private JwtUtils jwtUtils;

    @Test
    void shouldFailWhenNoExercisesExist() throws Exception {
        JSONObject response = new JSONObject();
        response.put("message", "Exercises not found");

        endpointRequest()
                .get("/exercises")
                .thenAssert()
                .withCode(404)
                .withResponse(response);
    }

    @Test
    void adminCreatesUpdatesAndDeletesAnExercise() throws Exception {
        String token = createAdminToken();
        int id = createAnExercise(token);
        updateAnExercise(token, id);
        deleteAnExercise(token, id);
        exerciseDoesNotExist(id);

    }

    private int createAnExercise(String token) throws Exception {
        JSONObject body = new JSONObject();
        body.put("name", "Exercise name");
        body.put("description", "Exercise description");
        body.put("difficulty", 5);

        JSONObject result = endpointRequest()
                .post("/exercise")
                .authorization(token)
                .body(body)
                .thenAssert()
                .withCode(201)
                .getResponseBody();

        return (int) result.get("id");
    }

    private void updateAnExercise(String token, int id) throws Exception {
        JSONObject body = new JSONObject();
        body.put("name", "Exercise name");
        body.put("description", "Exercise description");
        body.put("difficulty", 5);

        endpointRequest()
                .put("/exercise/" + id)
                .authorization(token)
                .body(body)
                .thenAssert()
                .withCode(204);
    }

    private void deleteAnExercise(String token, int id) throws Exception {
        endpointRequest()
                .delete("/exercise/" + id)
                .authorization(token)
                .thenAssert()
                .withCode(204);
    }

    private void exerciseDoesNotExist(int id) throws Exception {
        endpointRequest()
                .get("/exercise/" + id)
                .thenAssert()
                .withCode(404);
    }

    private String createAdminToken() {
        HashMap<String, Object> claims = new HashMap<>();
        claims.put("role", "ADMIN");
        return "Bearer " + jwtUtils.generateToken(UUID.randomUUID().toString(), claims);
    }
}
