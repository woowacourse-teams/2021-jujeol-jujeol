package com.jujeol.testtool.option;

import com.jujeol.member.fixture.TestMember;
import com.jujeol.testtool.request.TestAdapterContainer;
import com.jujeol.testtool.response.HttpResponse;
import com.jujeol.testtool.util.RequestDto;
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
            Object data,
            Object... pathVariables
    ) {
        this.requestDto = new RequestDto(httpMethod, url, testTool, data, pathVariables);
        this.testAdapterContainer = testAdapterContainer;
    }

    public RequestOption withDocument(String identifier) {
        requestDto.setIdentifier(identifier);
        return this;
    }

    public RequestOption withUser(TestMember testMember) {
        requestDto.setTestMember(testMember);
        return this;
    }

    public RequestOption withUser(String token) {
        requestDto.setToken(token);
        return this;
    }

    public RequestOption withUser() {
        requestDto.setTestMember(TestMember.RANDOM_MEMBER);
        return this;
    }

    public RequestOption withoutLog() {
        requestDto.notLogging();
        return this;
    }

    public RequestOption addMultipart(String name, Object contentBody) {
        requestDto.addFormData(name, contentBody);
        return this;
    }

    public HttpResponse build() {
        return testAdapterContainer.execute(requestDto);
    }
}
