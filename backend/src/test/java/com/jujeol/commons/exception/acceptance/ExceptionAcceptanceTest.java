package com.jujeol.commons.exception.acceptance;

import static org.assertj.core.api.Assertions.assertThat;

import com.jujeol.AcceptanceTest;
import com.jujeol.commons.exception.ExceptionCodeAndDetails;
import com.jujeol.commons.exception.JujeolExceptionDto;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

public class ExceptionAcceptanceTest extends AcceptanceTest {

    @DisplayName("404 Exception 확인")
    @Test
    public void notFoundExceptionTest() {
        ExtractableResponse<Response> response = request()
                .get("/neverexistapi/never/and/ever")
                .withDocument("exception/notfound")
                .build().totalResponse();

        int statusCode = response.statusCode();
        JujeolExceptionDto body = response.body().as(JujeolExceptionDto.class);

        assertThat(statusCode).isEqualTo(HttpStatus.NOT_FOUND.value());
        assertThat(body.getCode()).isEqualTo(ExceptionCodeAndDetails.NOT_FOUND_API.getCode());
        assertThat(body.getMessage()).isEqualTo(ExceptionCodeAndDetails.NOT_FOUND_API.getMessage());
    }
}
