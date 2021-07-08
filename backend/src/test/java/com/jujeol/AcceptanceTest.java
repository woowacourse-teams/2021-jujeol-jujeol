package com.jujeol;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@ActiveProfiles("test")
public class AcceptanceTest {

    @LocalServerPort
    int port;
    boolean logFlag;

    @BeforeEach
    public void setUp() {
        RestAssured.port = port;
        logFlag = true;
    }

    public void showLog(boolean logFlag) {
        this.logFlag = logFlag;
    }

    public ExtractableResponse<Response> invokeHttpGet(String path, Object... pathParams) {
        if (!logFlag) {
            return RestAssured
                    .given()
                    .when().get(path, pathParams)
                    .then()
                    .extract();
        }

        return RestAssured
                .given().log().all()
                .when().get(path, pathParams)
                .then().log().all()
                .extract();
    }

    public <T> ExtractableResponse<Response> invokeHttpPost(String path, T data) {
        if (!logFlag) {
            return RestAssured
                    .given()
                    .body(data).contentType(ContentType.JSON)
                    .post(path)
                    .then()
                    .extract();
        }

        return RestAssured
                .given().log().all()
                .body(data).contentType(ContentType.JSON)
                .post(path)
                .then().log().all()
                .extract();
    }
}
