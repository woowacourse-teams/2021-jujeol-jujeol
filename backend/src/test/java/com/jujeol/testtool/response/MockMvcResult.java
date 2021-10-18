package com.jujeol.testtool.response;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jujeol.commons.dto.CommonResponse;
import com.jujeol.commons.dto.PageInfo;
import com.jujeol.commons.exception.JujeolExceptionDto;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.ResultActions;

public class MockMvcResult implements HttpResponse {

    private final ResultActions resultActions;
    private final ObjectMapper objectMapper;

    public MockMvcResult(ResultActions resultActions, ObjectMapper objectMapper) {
        this.resultActions = resultActions;
        this.objectMapper = objectMapper;
    }

    @Override
    public <T> T convertBody(Class<T> tClass) {
        try {
            String json = resultActions.andReturn().getResponse().getContentAsString();
            CommonResponse responseDto = objectMapper.readValue(json, CommonResponse.class);
            final LinkedHashMap data = (LinkedHashMap) responseDto.getData();
            return objectMapper.convertValue(data, tClass);
        } catch (UnsupportedEncodingException | JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public <T> List<T> convertBodyToList(Class<T> tClass) {
        try {
            final String json = resultActions.andReturn().getResponse().getContentAsString();
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
        } catch (UnsupportedEncodingException | JsonProcessingException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    @Override
    public HttpStatus statusCode() {
        return HttpStatus.valueOf(resultActions.andReturn().getResponse().getStatus());
    }

    @Override
    public JujeolExceptionDto errorResponse() {
        try {
            String json = resultActions.andReturn().getResponse().getContentAsString();
            return objectMapper.readValue(json, JujeolExceptionDto.class);
        } catch (UnsupportedEncodingException | JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public PageInfo pageInfo() {
        try {
            String json = resultActions.andReturn().getResponse().getContentAsString();
            CommonResponse responseDto = objectMapper.readValue(json, CommonResponse.class);
            return responseDto.getPageInfo();
        } catch (UnsupportedEncodingException | JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }
}
