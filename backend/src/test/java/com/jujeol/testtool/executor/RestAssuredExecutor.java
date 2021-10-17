package com.jujeol.testtool.executor;

import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessRequest;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.restassured3.RestAssuredRestDocumentation.document;
import static org.springframework.restdocs.restassured3.RestAssuredRestDocumentation.documentationConfiguration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jujeol.member.auth.application.LoginService;
import com.jujeol.member.auth.application.dto.SocialProviderCodeDto;
import com.jujeol.member.auth.domain.ProviderName;
import com.jujeol.member.fixture.TestMember;
import com.jujeol.testtool.response.HttpResponse;
import com.jujeol.testtool.response.RestAssuredResult;
import com.jujeol.testtool.util.RequestDto;
import com.jujeol.testtool.util.TestTool;
import io.restassured.RestAssured;
import io.restassured.builder.MultiPartSpecBuilder;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import org.apache.commons.io.Charsets;
import org.springframework.http.HttpMethod;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.web.multipart.MultipartFile;

public class RestAssuredExecutor implements TestAdapter {

    private RestDocumentationContextProvider restDocumentation;

    private LoginService loginService;
    private ObjectMapper objectMapper;

    public RestAssuredExecutor(LoginService loginService, ObjectMapper objectMapper) {
        this.loginService = loginService;
        this.objectMapper = objectMapper;
    }

    @Override
    public void setDocumentConfig(RestDocumentationContextProvider restDocumentation) {
        this.restDocumentation = restDocumentation;
    }

    @Override
    public boolean isAssignable(RequestDto requestDto) {
        return TestTool.REST_ASSURED.equals(requestDto.getTestTool());
    }

    @Override
    public HttpResponse execute(RequestDto requestDto) {
        RequestSpecification requestSpecification = getDocument(requestDto.getIdentifier());
        if (Objects.isNull(requestDto.getToken()) || requestDto.getToken().isEmpty()) {
            requestSpecification = getUser(requestDto.getToken(), requestSpecification);
        } else {
            requestSpecification = getUser(requestDto.getTestMember(), requestSpecification);
        }

        if (requestDto.isLogFlag()) {
            requestSpecification = requestSpecification.log().all();
        }

        requestSpecification = putMultiPartFormData(requestDto.getFormData(), requestSpecification);

//        queryCounter.startCount();
//        ValidatableResponse validatableResponse = request.doAction(requestSpec);
//        final QueryResult queryResult = queryCounter.endCount();
//
//        if (requestDto.isLogFlag()) {
//            validatableResponse = validatableResponse.log().all();
//            queryResult.printLog();
//        }

        ValidatableResponse validatableResponse = urlRequest(requestSpecification, requestDto);

        if (requestDto.isLogFlag()) {
            validatableResponse = validatableResponse.log().all();
        }

        return new RestAssuredResult(validatableResponse.extract(), objectMapper);
    }

    private RequestSpecification putMultiPartFormData(Map<String, Object> formData, RequestSpecification requestSpecification) {
        if (formData.isEmpty()) {
            return requestSpecification;
        }

        requestSpecification.contentType("multipart/form-data");

        for (Entry<String, Object> stringObjectEntry : formData.entrySet()) {
            String name = stringObjectEntry.getKey();
            Object contentBody = stringObjectEntry.getValue();

            if(contentBody instanceof MultipartFile) {
                contentBody = toFile((MultipartFile) contentBody);
            }

            if(contentBody instanceof File) {
                requestSpecification = requestSpecification.multiPart(
                        new MultiPartSpecBuilder(contentBody)
                                .controlName(name)
                                .build()
                );
                continue;
            }

            requestSpecification = requestSpecification.multiPart(
                    new MultiPartSpecBuilder(contentBody)
                            .controlName(name)
                            .charset(Charsets.UTF_8)
                            .build()
            );
        }

        return requestSpecification;
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

    private RequestSpecification getDocument(String identifier) {
        if (Objects.isNull(identifier) || identifier.isEmpty() || Objects.isNull(restDocumentation)) {
            return RestAssured.given();
        }

        RequestSpecification spec = new RequestSpecBuilder()
                .addFilter(documentationConfiguration(restDocumentation))
                .build();

        return RestAssured.given(spec)
                .filter(document(identifier,
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint())));
    }

    private RequestSpecification getUser(TestMember testMember,
            RequestSpecification requestSpecification) {

        if (testMember == null) {
            return requestSpecification;
        }

        String token = loginService
                .createToken(SocialProviderCodeDto.create(testMember.getMatchedCode(),
                        ProviderName.TEST)).getAccessToken();

        return getUser(token, requestSpecification);
    }

    private RequestSpecification getUser(String token, RequestSpecification requestSpecification) {

        if (token == null || token.isEmpty()) {
            return requestSpecification;
        }

        return requestSpecification.header("Authorization", "Bearer " + token);
    }

    private ValidatableResponse urlRequest(RequestSpecification requestSpecification,
            RequestDto requestDto) {
        final HttpMethod httpMethod = requestDto.getHttpMethod();
        if (httpMethod.matches("GET")) {
            return requestSpecification.get(requestDto.getUrl(), requestDto.getPathVariables())
                    .then();
        }
        if (httpMethod.matches("POST")) {
            if(Objects.nonNull(requestDto.getData()) && requestDto.getFormData().isEmpty()) {
                requestSpecification.body(requestDto.getData())
                .contentType(ContentType.JSON);
            }
            return requestSpecification.post(requestDto.getUrl(), requestDto.getPathVariables())
                    .then();
        }
        if (httpMethod.matches("PUT")) {
            if(Objects.nonNull(requestDto.getData()) && requestDto.getFormData().isEmpty()) {
                requestSpecification.body(requestDto.getData())
                        .contentType(ContentType.JSON);
            }
            return requestSpecification.put(requestDto.getUrl(), requestDto.getPathVariables())
                    .then();
        }
        if (httpMethod.matches("DELETE")) {
            return requestSpecification.delete(requestDto.getUrl(), requestDto.getPathVariables())
                    .then();
        }
        return null;
    }
}
