package com.jujeol.testtool.option;

import com.jujeol.testtool.util.RequestDto;
import com.jujeol.testtool.response.RequestResult;
import com.jujeol.testtool.request.TestAdapterContainer;
import com.jujeol.testtool.util.TestTool;
import org.springframework.http.HttpMethod;

public class RequestOption {

    private final RequestDto requestDto;
    private final TestAdapterContainer testAdapterContainer;

    public RequestOption(
            HttpMethod httpMethod,
            String url,
            TestTool testTool,
            TestAdapterContainer testAdapterContainer,
            Object... pathVariables
    ) {
        this.requestDto = new RequestDto(httpMethod, url, testTool, pathVariables);
        this.testAdapterContainer = testAdapterContainer;
    }

    public RequestOption withDocument(String identifier) {
        requestDto.setIdentifier(identifier);
        return this;
    }

    public RequestResult flush() {
        return testAdapterContainer.execute(requestDto);
    }
}
