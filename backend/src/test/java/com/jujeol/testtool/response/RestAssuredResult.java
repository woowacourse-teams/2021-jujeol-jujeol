package com.jujeol.testtool.response;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jujeol.commons.dto.CommonResponse;
import com.jujeol.commons.dto.PageInfo;
import com.jujeol.commons.exception.JujeolExceptionDto;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import org.springframework.http.HttpStatus;

public class RestAssuredResult implements HttpResponse {

    private final ExtractableResponse<Response> response;
    private final ObjectMapper objectMapper;

    public RestAssuredResult(
            ExtractableResponse<Response> response,
            ObjectMapper objectMapper) {
        this.response = response;
        this.objectMapper = objectMapper;
    }

    public <T> T convertBody(Class<T> tClass) {
        final CommonResponse responseDto
                = response.body().as(CommonResponse.class);

        final LinkedHashMap data = (LinkedHashMap) responseDto.getData();
        return objectMapper.convertValue(data, tClass);
    }

    public <T> List<T> convertBodyToList(Class<T> tClass) {
        final String json = response.asString();
        try {
            final JsonNode jsonNode = objectMapper.readTree(json);

            final List<T> list = new ArrayList<>();
            final Iterator<JsonNode> data = jsonNode.withArray("data").elements();

            data.forEachRemaining(dataNode -> {
                try {
                    final T hello = objectMapper.treeToValue(dataNode, tClass);
                    list.add(hello);
                } catch (JsonProcessingException e) {
                    e.printStackTrace();
                }
            });
            return list;
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public HttpStatus statusCode() {
        return HttpStatus.valueOf(response.statusCode());
    }

    public JujeolExceptionDto errorResponse() {
        return response.as(JujeolExceptionDto.class);
    }

    public PageInfo pageInfo() {
        return response.body().as(CommonResponse.class).getPageInfo();
    }

//    public Long queryCount() {
//        return queryResult.queryCount();
//    }

    public ExtractableResponse<Response> totalResponse() {
        return response;
    }
}
