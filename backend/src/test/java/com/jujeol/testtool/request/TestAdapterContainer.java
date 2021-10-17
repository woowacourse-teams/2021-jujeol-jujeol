package com.jujeol.testtool.request;

import com.jujeol.testtool.executor.MockMvcExecutor;
import com.jujeol.testtool.executor.RestAssuredExecutor;
import com.jujeol.testtool.executor.TestAdapter;
import com.jujeol.testtool.response.RequestResult;
import com.jujeol.testtool.util.RequestDto;
import java.util.Arrays;
import java.util.List;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.stereotype.Component;

@Component
public class TestAdapterContainer {

    private final List<TestAdapter> testAdapters;

    public TestAdapterContainer() {
        this.testAdapters = Arrays.asList(
                new RestAssuredExecutor(),
                new MockMvcExecutor()
        );
    }

    public RequestResult execute(RequestDto requestDto) {
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
