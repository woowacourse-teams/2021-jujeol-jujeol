package com.jujeol.oauth.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.Map;

@RequiredArgsConstructor
@Component
public class ClientResponseConverter {

    private final ObjectMapper objectMapper;

    public <T> MultiValueMap<String, String> convertHttpBody(T body) {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        final Map<String, String> map =
                objectMapper.convertValue(body, new TypeReference<>() {
                });
        params.setAll(map);
        return params;
    }

    public String extractDataAsString(String json, String dataName) {
        try {
            return objectMapper.readTree(json).get(dataName).asText();
        } catch (JsonProcessingException e) {
            throw new IllegalStateException();
        }
    }
}
