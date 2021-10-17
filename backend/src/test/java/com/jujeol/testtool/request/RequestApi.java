package com.jujeol.testtool.request;

import com.jujeol.testtool.option.RequestOption;
import com.jujeol.testtool.util.TestTool;
import org.springframework.http.HttpMethod;

public class RequestApi {

    private final TestTool testTool;
    private final TestAdapterContainer testAdapterContainer;

    public RequestApi(TestTool testTool, TestAdapterContainer testAdapterContainer) {
        this.testTool = testTool;
        this.testAdapterContainer = testAdapterContainer;
    }

    public RequestOption get(String url, Object... pathVariables) {
        return getRequestOption(HttpMethod.GET, url, pathVariables);
    }

    public RequestOption post(String url, Object data, Object... pathVariables) {
        return getRequestOption(HttpMethod.POST, url, pathVariables);
    }

    public RequestOption put(String url, Object data, Object... pathVariables) {
        return getRequestOption(HttpMethod.PUT, url, pathVariables);
    }

    public RequestOption delete(String url, Object... pathVariables) {
        return getRequestOption(HttpMethod.DELETE, url, pathVariables);
    }

    private RequestOption getRequestOption(
            HttpMethod httpMethod,
            String url,
            Object... pathVariables
    ) {
        return new RequestOption(httpMethod, url, testTool, testAdapterContainer, pathVariables);
    }
}
