package com.jujeol.testtool;

public interface TestAdapter {

    boolean isAssignable(RequestDto requestDto);

    RequestResult execute(RequestDto requestDto);
}
