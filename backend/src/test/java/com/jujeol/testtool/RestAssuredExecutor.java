package com.jujeol.testtool;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.springframework.http.HttpMethod;

public class RestAssuredExecutor implements TestAdapter {

    @Override
    public boolean isAssignable(RequestDto requestDto) {
        return TestTool.REST_ASSURED.equals(requestDto.getTestTool());
    }

    @Override
    public RequestResult execute(RequestDto requestDto) {
        final RequestSpecification requestSpecification = RestAssured.given();
        final Response response = urlRequest(requestSpecification, requestDto);
        return new RestAssuredResult(response);
    }

    private Response urlRequest(RequestSpecification requestSpecification, RequestDto requestDto) {
        final HttpMethod httpMethod = requestDto.getHttpMethod();
        if (httpMethod.matches("GET")) {
            return requestSpecification.get(requestDto.getUrl());
        }
        if (httpMethod.matches("POST")) {
            return requestSpecification.post(requestDto.getUrl());
        }
        if (httpMethod.matches("PUT")) {
            return requestSpecification.put(requestDto.getUrl());
        }
        if (httpMethod.matches("DELETE")) {
            return requestSpecification.delete(requestDto.getUrl());
        }
        return null;
    }
}
