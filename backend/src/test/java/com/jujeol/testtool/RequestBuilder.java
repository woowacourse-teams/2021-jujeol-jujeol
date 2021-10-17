package com.jujeol.testtool;

import com.jujeol.testtool.request.RequestApi;
import com.jujeol.testtool.request.TestAdapterContainer;
import com.jujeol.testtool.util.TestTool;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.stereotype.Component;

@Component
public class RequestBuilder {

    private final TestAdapterContainer testAdapterContainer;

    public RequestBuilder(TestAdapterContainer testAdapterContainer) {
        this.testAdapterContainer = testAdapterContainer;
    }

    public RequestApi builder(TestTool testTool){
        return new RequestApi(testTool, testAdapterContainer);
    }

    public RequestApi builder(TestTool testTool, RestDocumentationContextProvider restDocumentation){
        testAdapterContainer.setDocumentConfig(restDocumentation);
        return new RequestApi(testTool, testAdapterContainer);
    }
}
