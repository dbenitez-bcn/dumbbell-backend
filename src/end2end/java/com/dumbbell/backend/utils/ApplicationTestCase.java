package com.dumbbell.backend.utils;


import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.ResultMatcher;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.request;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public abstract class ApplicationTestCase {

    @Autowired
    private MockMvc mockMvc;

    protected void assertResponse(
            String endpoint,
            Integer expectedStatusCode,
            String expectedResponse
    ) throws Exception {
        ResultMatcher response = expectedResponse.isEmpty()
                ? content().string("")
                : content().json(expectedResponse);

        mockMvc
                .perform(get(endpoint))
                .andExpect(status().is(expectedStatusCode))
                .andExpect(response);
    }

    protected void assertResponse(
            HttpMethod method,
            String endpoint,
            String body,
            Integer expectedStatusCode,
            String expectedResponse
    ) throws Exception {
        ResultMatcher response = expectedResponse.isEmpty()
                ? content().string("")
                : content().json(expectedResponse);

        mockMvc
                .perform(request(method, endpoint).content(body)
                        .contentType(APPLICATION_JSON))
                .andExpect(status().is(expectedStatusCode))
                .andExpect(response);
    }

    protected void assertResponse(
            String endpoint,
            Integer expectedStatusCode,
            String expectedResponse,
            HttpHeaders headers
    ) throws Exception {
        ResultMatcher response = expectedResponse.isEmpty()
                ? content().string("")
                : content().json(expectedResponse);

        mockMvc
                .perform(get(endpoint).headers(headers))
                .andExpect(status().is(expectedStatusCode))
                .andExpect(response);
    }

    protected void assertRequestWithBody(
            HttpMethod method,
            String endpoint,
            JSONObject body,
            Integer expectedStatusCode
    ) throws Exception {
        mockMvc
                .perform(request(method, endpoint).content(body.toString())
                        .contentType(APPLICATION_JSON))
                .andExpect(status().is(expectedStatusCode));
    }

    protected void assertRequestWithBody(
            HttpMethod method,
            String endpoint,
            String body,
            Integer expectedStatusCode,
            String expectedContent
    ) throws Exception {
        mockMvc
                .perform(request(method, endpoint).content(body)
                        .contentType(APPLICATION_JSON))
                .andExpect(status().is(expectedStatusCode))
                .andExpect(content().string(expectedContent));
    }

    protected void assertRequest(
            HttpMethod method,
            String endpoint,
            Integer expectedStatusCode
    ) throws Exception {
        mockMvc
                .perform(request(method, endpoint))
                .andExpect(status().is(expectedStatusCode))
                .andExpect(content().string(""));
    }

    protected RequestAssertor assertRequest(
            HttpMethod method,
            String endpoint
    ) throws Exception {
        ResultActions perform = mockMvc.perform(request(method, endpoint));
        return new RequestAssertor(perform);
    }

    protected RequestAssertor assertRequest(
            HttpMethod method,
            String endpoint,
            JSONObject body,
            String token
    ) throws Exception {
        ResultActions perform = mockMvc.perform(
                request(method, endpoint)
        .content(body.toString())
        .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
        .header(HttpHeaders.CONTENT_TYPE, "application/json"));
        return new RequestAssertor(perform);
    }

    protected static class RequestAssertor {
        private final ResultActions sut;

        private RequestAssertor(ResultActions sut) {
            this.sut = sut;
        }

        public RequestAssertor withCode(int expectedCode) throws Exception {
            sut.andExpect(status().is(expectedCode));
            return this;
        }

        public RequestAssertor withResponse(JSONObject expectedResponse) throws Exception {
            sut.andExpect(content().json(expectedResponse.toString()));
            return this;
        }

        public JSONObject getResponseBody() {
            try {
                return new JSONObject(
                        sut.andReturn().getResponse().getContentAsString()
                );
            } catch (Exception e) {
                return new JSONObject();
            }
        }
    }
}