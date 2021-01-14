package com.dumbbell.backend.exercises;

import com.dumbbell.backend.core.utils.JwtUtils;
import com.dumbbell.backend.utils.ApplicationTestCase;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;

import java.util.HashMap;
import java.util.UUID;

public class ExercisesControllerE2E extends ApplicationTestCase {

    @Autowired
    private JwtUtils jwtUtils;

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

    @Test
    void adminCreatesUpdatesAndDeletesAnExercise() throws Exception {
        String token = createAdminToken();
        int id = createAnExercise(token);
        updateAnExercise(token, id);
        deleteAnExercise(token, id);

    }

    private int createAnExercise(String token) throws Exception {
        JSONObject body = new JSONObject();
        body.put("name", "Exercise name");
        body.put("description", "Exercise description");
        body.put("difficulty", 5);

        JSONObject result = assertRequest(HttpMethod.POST, "/exercise", body, token)
                .withCode(201)
                .getResponseBody();

        return (int) result.get("id");
    }

    private void updateAnExercise(String token, int id) throws Exception {
        JSONObject body = new JSONObject();
        body.put("name", "Exercise name");
        body.put("description", "Exercise description");
        body.put("difficulty", 5);

        assertRequest(HttpMethod.PUT, "/exercise/" + id, body, token)
                .withCode(204);
    }

    private void deleteAnExercise(String token, int id) throws Exception {

        assertRequest(HttpMethod.DELETE, "/exercise/" + id, new JSONObject(), token)
                .withCode(204);
    }

    private String createAdminToken() {
        HashMap<String, Object> claims = new HashMap<>();
        claims.put("role", "ADMIN");
        return jwtUtils.generateToken(UUID.randomUUID().toString(), claims);
    }
}
