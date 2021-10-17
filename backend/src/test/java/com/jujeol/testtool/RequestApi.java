package com.jujeol.testtool;

import org.springframework.http.HttpMethod;

public class RequestApi {

    private final TestTool testTool;
    private final TestAdapterContainer testAdapterContainer;

    public RequestApi(TestTool testTool, TestAdapterContainer testAdapterContainer) {
        this.testTool = testTool;
        this.testAdapterContainer = testAdapterContainer;
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
        return new RequestOption(httpMethod, url, testTool, testAdapterContainer);
    }
}
