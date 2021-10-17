package com.jujeol.testtool;

public class RequestBuilder {

    public static RequestApi build(TestTool testTool){
        return new RequestApi(testTool);
    };
}
