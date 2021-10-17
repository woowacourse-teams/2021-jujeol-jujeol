package com.jujeol.testtool;

import org.springframework.stereotype.Component;

@Component
public class RequestBuilder {

    private final TestAdapterContainer testAdapterContainer;

    public RequestBuilder(TestAdapterContainer testAdapterContainer) {
        this.testAdapterContainer = testAdapterContainer;
    }

    public RequestApi build(TestTool testTool){
        return new RequestApi(testTool, testAdapterContainer);
    }
}
