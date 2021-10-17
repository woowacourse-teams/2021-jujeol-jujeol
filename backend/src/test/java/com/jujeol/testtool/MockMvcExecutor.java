package com.jujeol.testtool;

public class MockMvcExecutor implements TestAdapter {

    @Override
    public boolean isAssignable(RequestDto requestDto) {
        return false;
    }

    @Override
    public RequestResult execute(RequestDto requestDto) {
        return null;
    }
}
