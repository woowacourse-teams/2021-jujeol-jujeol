package com.jujeol.testtool.executor;

import com.jujeol.testtool.response.HttpResponse;
import com.jujeol.testtool.util.RequestDto;
import org.springframework.restdocs.RestDocumentationContextProvider;

public class MockMvcExecutor implements TestAdapter {

    @Override
    public void setDocumentConfig(RestDocumentationContextProvider restDocumentation) {

    }

    @Override
    public boolean isAssignable(RequestDto requestDto) {
        return false;
    }

    @Override
    public HttpResponse execute(RequestDto requestDto) {
        return null;
    }
}
