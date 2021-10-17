package com.jujeol.testtool.executor;

import com.jujeol.testtool.util.RequestDto;
import com.jujeol.testtool.response.RequestResult;
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
    public RequestResult execute(RequestDto requestDto) {
        return null;
    }
}
