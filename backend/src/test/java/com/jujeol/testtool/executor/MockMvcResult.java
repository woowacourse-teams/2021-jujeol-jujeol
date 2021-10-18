package com.jujeol.testtool.executor;

import com.jujeol.commons.dto.PageInfo;
import com.jujeol.commons.exception.JujeolExceptionDto;
import com.jujeol.testtool.response.HttpResponse;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.ResultActions;

public class MockMvcResult implements HttpResponse {

    private final ResultActions resultActions;

    public MockMvcResult(ResultActions resultActions) {
        this.resultActions = resultActions;
    }

    @Override
    public <T> T convertBody(Class<T> tClass) {
        return null;
    }

    @Override
    public <T> List<T> convertBodyToList(Class<T> tClass) {
        return null;
    }

    @Override
    public HttpStatus statusCode() {
        return null;
    }

    @Override
    public JujeolExceptionDto errorResponse() {
        return null;
    }

    @Override
    public PageInfo pageInfo() {
        return null;
    }

    @Override
    public ExtractableResponse<Response> totalResponse() {
        return null;
    }
}
