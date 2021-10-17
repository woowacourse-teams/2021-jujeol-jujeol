package com.jujeol.testtool;

import java.util.Arrays;
import java.util.List;
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
}
