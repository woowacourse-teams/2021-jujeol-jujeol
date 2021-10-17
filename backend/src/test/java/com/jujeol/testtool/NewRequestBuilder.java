package com.jujeol.testtool;

import com.jujeol.testtool.request.RequestApi;
import com.jujeol.testtool.request.TestAdapterContainer;
import com.jujeol.testtool.util.TestTool;
import org.springframework.restdocs.RestDocumentationContextProvider;

public class NewRequestBuilder {

    private final TestAdapterContainer testAdapterContainer;
    private final TestTool testTool;
    private RestDocumentationContextProvider restDocumentationContextProvider;

    public NewRequestBuilder(TestAdapterContainer testAdapterContainer, TestTool testTool) {
        this.testAdapterContainer = testAdapterContainer;
        this.testTool = testTool;
    }

    public RequestApi builder(){
        testAdapterContainer.setDocumentConfig(restDocumentationContextProvider);
        return new RequestApi(testTool, testAdapterContainer);
    }

    public void setRestDocumentationContextProvider(RestDocumentationContextProvider restDocumentationContextProvider) {
        this.restDocumentationContextProvider = restDocumentationContextProvider;
    }
}
