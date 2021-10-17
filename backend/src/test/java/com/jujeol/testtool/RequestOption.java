package com.jujeol.testtool;

import org.springframework.http.HttpMethod;

public class RequestOption {

    private final RequestDto requestDto;
    private final TestAdapterContainer testAdapterContainer;

    public RequestOption(
            HttpMethod httpMethod,
            String url,
            TestTool testTool,
            TestAdapterContainer testAdapterContainer
    ) {
        this.requestDto = new RequestDto(httpMethod, url, testTool);
        this.testAdapterContainer = testAdapterContainer;
    }

    public RequestResult flush() {
        return testAdapterContainer.execute(requestDto);
    }
}
