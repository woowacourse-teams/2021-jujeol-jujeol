package com.jujeol.testtool.util;

import org.springframework.restdocs.RestDocumentationContextProvider;

public class RestAssuredTool {

    private RestDocumentationContextProvider restDocumentation;

    public RestAssuredTool() {
    }

    public RestAssuredTool(
            RestDocumentationContextProvider restDocumentation
    ) {
        this.restDocumentation = restDocumentation;
    }

    public RestDocumentationContextProvider getRestDocumentation() {
        return restDocumentation;
    }
}
