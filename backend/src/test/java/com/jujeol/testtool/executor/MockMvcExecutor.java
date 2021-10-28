package com.jujeol.testtool.executor;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jujeol.member.auth.application.LoginService;
import com.jujeol.member.auth.application.dto.SocialProviderCodeDto;
import com.jujeol.member.auth.domain.ProviderName;
import com.jujeol.member.fixture.TestMember;
import com.jujeol.testtool.response.HttpResponse;
import com.jujeol.testtool.response.MockMvcResult;
import com.jujeol.testtool.util.RequestDto;
import com.jujeol.testtool.util.TestTool;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Map.Entry;
import java.util.Objects;
import org.apache.commons.io.FileUtils;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMultipartHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.multipart.MultipartFile;

public class MockMvcExecutor implements TestAdapter {

    private final WebApplicationContext context;
    private final ObjectMapper objectMapper;
    private final LoginService loginService;

    private RestDocumentationContextProvider restDocumentation;

    public MockMvcExecutor(
            WebApplicationContext context,
            ObjectMapper objectMapper,
            LoginService loginService
    ) {
        this.context = context;
        this.objectMapper = objectMapper;
        this.loginService = loginService;
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
                .apply(documentationConfiguration(restDocumentation)
                        .operationPreprocessors()
                        .withRequestDefaults(prettyPrint())
                        .withResponseDefaults(prettyPrint()))
                .addFilters(new CharacterEncodingFilter("UTF-8", true))
                .build();

        try {
            MockHttpServletRequestBuilder servletRequestBuilder;

            if (requestDto.getFormData().isEmpty()) {
                servletRequestBuilder = urlRequest(requestDto);
            } else {
                servletRequestBuilder = multipartRequest(requestDto);
            }

            if (Objects.isNull(requestDto.getToken()) || requestDto.getToken().isEmpty()) {
                servletRequestBuilder = getUser(requestDto.getTestMember(), servletRequestBuilder);
            } else {
                servletRequestBuilder = getUser(requestDto.getToken(), servletRequestBuilder);
            }

            ResultActions resultActions = mockMvc.perform(servletRequestBuilder);

            if (requestDto.isLogFlag()) {
                resultActions = resultActions.andDo(print());
            }

            if (requestDto.getIdentifier() != null && !requestDto.getIdentifier().isEmpty()) {
                resultActions = resultActions.andDo(document(requestDto.getIdentifier()));
            }

            return new MockMvcResult(resultActions, objectMapper);
        } catch (Exception e) {
            e.printStackTrace();
            throw new IllegalArgumentException("MockMvcExecutor execute 문제");
        }
    }

    private MockHttpServletRequestBuilder multipartRequest(RequestDto requestDto)
            throws IOException {
        MockHttpServletRequestBuilder servletRequestBuilder = MockMvcRequestBuilders
                .multipart(requestDto.getUrl(), requestDto.getPathVariables());

        for (Entry<String, Object> stringObjectEntry : requestDto.getFormData().entrySet()) {
            String name = stringObjectEntry.getKey();
            Object contentBody = stringObjectEntry.getValue();

            if (contentBody instanceof MultipartFile) {
                contentBody = toFile((MultipartFile) contentBody);
            }

            if (contentBody instanceof File) {
                MockMultipartHttpServletRequestBuilder tempBuilder = (MockMultipartHttpServletRequestBuilder) servletRequestBuilder;
                servletRequestBuilder = tempBuilder.file(
                        new MockMultipartFile(name, name, "image/png",
                                FileUtils.readFileToByteArray((File) contentBody))
                );
                continue;
            }
            servletRequestBuilder = servletRequestBuilder
                    .param(name, String.valueOf(contentBody));
        }

        if (requestDto.getHttpMethod().matches("PUT")) {
            servletRequestBuilder = servletRequestBuilder
                    .with(request -> {
                        request.setMethod("PUT");
                        return request;
                    });
        }
        return servletRequestBuilder;
    }

    private File toFile(MultipartFile image) {
        File convertedFile = new File(image.getName());
        try (FileOutputStream fos = new FileOutputStream(convertedFile)) {
            fos.write(image.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return convertedFile;
    }

    private MockHttpServletRequestBuilder getUser(TestMember testMember,
            MockHttpServletRequestBuilder servletRequestBuilder) {

        if (testMember == null) {
            return servletRequestBuilder;
        }

        String token = loginService
                .createToken(SocialProviderCodeDto.create(testMember.getMatchedCode(),
                        ProviderName.TEST)).getAccessToken();

        return getUser(token, servletRequestBuilder);
    }

    private MockHttpServletRequestBuilder getUser(
            String token,
            MockHttpServletRequestBuilder servletRequestBuilder
    ) {

        if (token == null || token.isEmpty()) {
            return servletRequestBuilder;
        }

        return servletRequestBuilder.header("Authorization", "Bearer " + token);
    }

    private MockHttpServletRequestBuilder urlRequest(RequestDto requestDto) throws Exception {
        final HttpMethod httpMethod = requestDto.getHttpMethod();
        if (httpMethod.matches("GET")) {
            return MockMvcRequestBuilders.get(requestDto.getUrl(), requestDto.getPathVariables());
        }

        if (httpMethod.matches("POST")) {
            if (Objects.nonNull(requestDto.getData())) {
                return MockMvcRequestBuilders
                        .post(requestDto.getUrl(), requestDto.getPathVariables())
                        .content(objectMapper.writeValueAsBytes(requestDto.getData()))
                        .contentType(MediaType.APPLICATION_JSON);
            }
            return MockMvcRequestBuilders.post(requestDto.getUrl(), requestDto.getPathVariables());
        }

        if (httpMethod.matches("PUT")) {
            if (Objects.nonNull(requestDto.getData())) {
                return MockMvcRequestBuilders
                        .put(requestDto.getUrl(), requestDto.getPathVariables())
                        .content(objectMapper.writeValueAsBytes(requestDto.getData()))
                        .contentType(MediaType.APPLICATION_JSON);
            }
            return MockMvcRequestBuilders.put(requestDto.getUrl(), requestDto.getPathVariables());
        }
        if (httpMethod.matches("DELETE")) {
            return MockMvcRequestBuilders
                    .delete(requestDto.getUrl(), requestDto.getPathVariables());
        }
        throw new IllegalArgumentException("not matched http method");
    }
}
