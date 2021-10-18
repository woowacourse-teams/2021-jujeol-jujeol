package com.jujeol.testtool.executor;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jujeol.testtool.response.HttpResponse;
import com.jujeol.testtool.util.RequestDto;
import com.jujeol.testtool.util.TestTool;
import java.util.Objects;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

public class MockMvcExecutor implements TestAdapter {

    private final WebApplicationContext context;
    private final ObjectMapper objectMapper;

    private RestDocumentationContextProvider restDocumentation;

    public MockMvcExecutor(WebApplicationContext context, ObjectMapper objectMapper) {
        this.context = context;
        this.objectMapper = objectMapper;
    }

    @Override
    public void setDocumentConfig(RestDocumentationContextProvider restDocumentation) {
        this.restDocumentation = restDocumentation;
    }

    @Override
    public boolean isAssignable(RequestDto requestDto) {
        return TestTool.MOCK_MVC.equals(requestDto.getTestTool());
    }

    @Override
    public HttpResponse execute(RequestDto requestDto) {
        final MockMvc mockMvc = MockMvcBuilders.webAppContextSetup(context)
                .apply(documentationConfiguration(restDocumentation))
                .build();
        try {
            final ResultActions resultActions = urlRequest(mockMvc, requestDto);
        } catch (Exception e) {
            // todo: exception 처리
            e.printStackTrace();
        }
        return new MockMvcResult();
    }

    private ResultActions urlRequest(MockMvc mockMvc,
            RequestDto requestDto) throws Exception {
        final HttpMethod httpMethod = requestDto.getHttpMethod();
        if (httpMethod.matches("GET")) {
            return mockMvc.perform(get(requestDto.getUrl(), requestDto.getPathVariables()));
        }

        if (httpMethod.matches("POST")) {
            if (Objects.nonNull(requestDto.getData())) {
                return mockMvc.perform(post(requestDto.getUrl())
                        .content(objectMapper.writeValueAsBytes(requestDto.getData()))
                        .contentType(MediaType.APPLICATION_JSON));
            }
            return mockMvc.perform(post(requestDto.getUrl(), requestDto.getPathVariables()));
        }

        if (httpMethod.matches("PUT")) {
            if (Objects.nonNull(requestDto.getData())) {
                return mockMvc.perform(put(requestDto.getUrl())
                        .content(objectMapper.writeValueAsBytes(requestDto.getData()))
                        .contentType(MediaType.APPLICATION_JSON));
            }
            return mockMvc.perform(put(requestDto.getUrl(), requestDto.getPathVariables()));
        }
        if (httpMethod.matches("DELETE")) {
            return mockMvc.perform(delete(requestDto.getUrl(), requestDto.getPathVariables()));
        }
        return null;
    }
}
