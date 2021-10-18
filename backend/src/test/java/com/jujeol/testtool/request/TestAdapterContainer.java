package com.jujeol.testtool.request;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jujeol.member.auth.application.LoginService;
import com.jujeol.member.auth.ui.interceptor.LoginInterceptor;
import com.jujeol.testtool.executor.MockMvcExecutor;
import com.jujeol.testtool.executor.RestAssuredExecutor;
import com.jujeol.testtool.executor.TestAdapter;
import com.jujeol.testtool.response.HttpResponse;
import com.jujeol.testtool.util.RequestDto;
import java.util.Arrays;
import java.util.List;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.stereotype.Component;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

@Component
public class TestAdapterContainer {

    private final List<TestAdapter> testAdapters;

    public TestAdapterContainer(
            LoginService loginService,
            ObjectMapper objectMapper,
            WebApplicationContext context
    ) {

        this.testAdapters = Arrays.asList(
                new RestAssuredExecutor(loginService, objectMapper),
                new MockMvcExecutor(context, objectMapper, loginService)
        );
    }

    public HttpResponse execute(RequestDto requestDto) {
        return testAdapters.stream()
                .filter(testAdapter -> testAdapter.isAssignable(requestDto))
                .map(testAdapter -> testAdapter.execute(requestDto))
                .findAny()
                .orElseThrow();
    }

    public void setDocumentConfig(RestDocumentationContextProvider restDocumentation) {
        for (TestAdapter testAdapter : testAdapters) {
            testAdapter.setDocumentConfig(restDocumentation);
        }
    }
}
