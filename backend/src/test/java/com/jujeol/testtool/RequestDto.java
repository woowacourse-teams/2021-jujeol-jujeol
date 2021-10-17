package com.jujeol.testtool;

import org.springframework.http.HttpMethod;

public class RequestDto {

    private final HttpMethod httpMethod;
    private final String url;
    private final TestTool testTool;

    public RequestDto(HttpMethod httpMethod, String url, TestTool testTool) {
        this.httpMethod = httpMethod;
        this.url = url;
        this.testTool = testTool;
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
}
