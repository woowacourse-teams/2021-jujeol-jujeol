package com.jujeol.testtool;

import io.restassured.response.Response;

public class RestAssuredResult implements RequestResult {

    private final Response response;

    public RestAssuredResult(Response response) {
        this.response = response;
    }
}
