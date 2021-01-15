package com.dumbbell.backend.exercises;

import com.dumbbell.backend.core.utils.JwtUtils;
import com.dumbbell.backend.utils.ApplicationTestCase;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

public class ExercisesControllerE2E extends ApplicationTestCase {

    @Autowired
    private JwtUtils jwtUtils;

    @Test
    void shouldFailWhenNoExercisesExist() throws Exception {
        Object result = endpointRequest()
                .get("/exercises")
                .authorization(createUserToken())
                .thenAssert()
                .withCode(404)
                .getResponseBody()
                .get("message");

        assertThat(result).isEqualTo("Exercises not found");
    }

    @Test
    void adminCreatesUpdatesAndDeletesAnExercise() throws Exception {
        String token = createAdminToken();
        JSONObject body = new JSONObject();
        body.put("name", "Exercise name");
        body.put("description", "Exercise description");
        body.put("difficulty", 5);

        int id = (int) endpointRequest()
                .post("/exercise")
                .authorization(token)
                .body(body)
                .thenAssert()
                .withCode(201)
                .getResponseBody()
                .get("id");

        endpointRequest()
                .put("/exercise/" + id)
                .authorization(token)
                .body(body)
                .thenAssert()
                .withCode(204);

        endpointRequest()
                .delete("/exercise/" + id)
                .authorization(token)
                .thenAssert()
                .withCode(204);

        endpointRequest()
                .get("/exercise/" + id)
                .authorization(token)
                .thenAssert()
                .withCode(404);

    }

    @Test
    void aUserCanNotCreateAnExercise() throws Exception {
        endpointRequest()
                .post("/exercise")
                .authorization(createUserToken())
                .thenAssert()
                .withCode(403);
    }

    @Test
    void aUserCanNotDeleteAnExercise() throws Exception {
        endpointRequest()
                .delete("/exercise/666")
                .authorization(createUserToken())
                .thenAssert()
                .withCode(403);
    }

    @Test
    void aUserCanNotUpdateAnExercise() throws Exception {
        endpointRequest()
                .put("/exercise/666")
                .authorization(createUserToken())
                .thenAssert()
                .withCode(403);
    }

    private String createAdminToken() {
        return createToken("ADMIN");
    }

    private String createUserToken() {
        return createToken("USER");
    }

    private String createToken(String role) {
        HashMap<String, Object> claims = new HashMap<>();
        claims.put("role", role);
        return "Bearer " + jwtUtils.generateToken(UUID.randomUUID().toString(), claims);
    }
}
