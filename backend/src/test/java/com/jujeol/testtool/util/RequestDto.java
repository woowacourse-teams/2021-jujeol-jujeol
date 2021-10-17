package com.jujeol.testtool.util;

import org.springframework.http.HttpMethod;

public class RequestDto {

    private final HttpMethod httpMethod;
    private final String url;
    private final TestTool testTool;
    private final Object[] pathVariables;
    private String identifier;

    public RequestDto(
            HttpMethod httpMethod,
            String url,
            TestTool testTool,
            Object... pathVariables
    ) {
        this.httpMethod = httpMethod;
        this.url = url;
        this.testTool = testTool;
        this.pathVariables = pathVariables;
    }

    public HttpMethod getHttpMethod() {
        return httpMethod;
    }

    public String getUrl() {
        return url;
    }

    public TestTool getTestTool() {
        return testTool;
    }

    public Object[] getPathVariables() {
        return pathVariables;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }
}
