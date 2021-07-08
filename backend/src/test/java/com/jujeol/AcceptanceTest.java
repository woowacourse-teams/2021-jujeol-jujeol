package com.jujeol;

import static org.springframework.restdocs.restassured3.RestAssuredRestDocumentation.documentationConfiguration;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@ExtendWith(RestDocumentationExtension.class)
@ActiveProfiles("test")
public class AcceptanceTest {

    @LocalServerPort
    private int port;
    private boolean logFlag;
    private RequestSpecification spec;

    @BeforeEach
    public void setUp(RestDocumentationContextProvider restDocumentation) {
        RestAssured.port = port;
        logFlag = true;
        spec = new RequestSpecBuilder()
                .addFilter(documentationConfiguration(restDocumentation))
                .build();
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
