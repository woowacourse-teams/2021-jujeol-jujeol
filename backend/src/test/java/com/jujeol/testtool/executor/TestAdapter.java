package com.jujeol.testtool.executor;

import com.jujeol.testtool.util.RequestDto;
import com.jujeol.testtool.response.RequestResult;
import org.springframework.restdocs.RestDocumentationContextProvider;

public interface TestAdapter {

    void setDocumentConfig(RestDocumentationContextProvider restDocumentation);

    boolean isAssignable(RequestDto requestDto);

    RequestResult execute(RequestDto requestDto);
}
