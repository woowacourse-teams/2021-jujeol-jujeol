package com.jujeol.testtool.response;

import com.jujeol.commons.dto.PageInfo;
import com.jujeol.commons.exception.JujeolExceptionDto;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import java.util.List;
import org.springframework.http.HttpStatus;

public interface HttpResponse {

    <T> T convertBody(Class<T> tClass);
    <T> List<T> convertBodyToList(Class<T> tClass);
    HttpStatus statusCode();
    JujeolExceptionDto errorResponse();
    PageInfo pageInfo();
    ExtractableResponse<Response> totalResponse();
}
