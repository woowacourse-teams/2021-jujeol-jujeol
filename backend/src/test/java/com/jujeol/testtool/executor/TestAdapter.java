package com.jujeol.testtool.executor;

import com.jujeol.testtool.response.HttpResponse;
import com.jujeol.testtool.util.RequestDto;
import org.springframework.restdocs.RestDocumentationContextProvider;

public interface TestAdapter {

    void setDocumentConfig(RestDocumentationContextProvider restDocumentation);

    boolean isAssignable(RequestDto requestDto);

    HttpResponse execute(RequestDto requestDto);
}
