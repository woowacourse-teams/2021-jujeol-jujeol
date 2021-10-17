package com.jujeol.testtool;

import org.springframework.http.HttpMethod;

public class RequestApi {

    private final TestTool testTool;

    public RequestApi(TestTool testTool) {
        this.testTool = testTool;
    }

    public RequestOption get(String url) {
        return getRequestOption(HttpMethod.GET, url);
    }

    public RequestOption post(String url) {
        return getRequestOption(HttpMethod.POST, url);
    }

    public RequestOption put(String url) {
        return getRequestOption(HttpMethod.PUT, url);
    }

    public RequestOption delete(String url) {
        return getRequestOption(HttpMethod.DELETE, url);
    }

    private RequestOption getRequestOption(HttpMethod httpMethod, String url) {
        return null;
    }
}
