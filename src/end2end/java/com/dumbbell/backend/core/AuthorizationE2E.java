package com.dumbbell.backend.core;

import com.dumbbell.backend.utils.ApplicationTestCase;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpMethod;

import java.util.Date;
import java.util.HashMap;
import java.util.UUID;

public class AuthorizationE2E extends ApplicationTestCase {
    @Test
    void givenWrongBearerFormat_shouldFail() throws Exception {
        endpointRequest()
                .path("/")
                .authorization("BaReR wrong_token")
                .method(HttpMethod.GET)
                .thenAssert()
                .withCode(403);
    }

    @Test
    void givenNoAuthorization_shouldFail() throws Exception {
        endpointRequest()
                .path("/")
                .authorization("")
                .method(HttpMethod.GET)
                .thenAssert()
                .withCode(403);
    }

    @Test
    void givenAToken_whenIsNotSigned_shouldFail() throws Exception {
        String wrongToken = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ.SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c";

        endpointRequest()
                .path("/")
                .authorization("Bearer " + wrongToken)
                .method(HttpMethod.GET)
                .thenAssert()
                .withCode(403);
    }

    @Test
    void givenAToken_whenIsExpired_shouldFail() throws Exception {
        String expiredToken = generateExpiredToken(UUID.randomUUID().toString());

        endpointRequest()
                .path("/")
                .authorization("Bearer " + expiredToken)
                .method(HttpMethod.GET)
                .thenAssert()
                .withCode(403);
    }

    @Test
    void givenAToken_whenSubjectIsNull_shouldFail() throws Exception {
        String expiredToken = generateExpiredToken(null);
        endpointRequest()
                .path("/")
                .authorization("Bearer " + expiredToken)
                .method(HttpMethod.GET)
                .thenAssert()
                .withCode(403);
    }

    private String generateExpiredToken(String subject) {
        HashMap<String, Object> claims = new HashMap<>();
        claims.put("role", "USER");
        return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() - 1000 * 60 * 60 * 10))
                .signWith(SignatureAlgorithm.HS256, "DEFAULT_KEY").compact();
    }
}
