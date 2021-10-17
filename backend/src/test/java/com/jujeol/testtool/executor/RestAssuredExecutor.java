package com.jujeol.testtool.executor;

import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessRequest;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.restassured3.RestAssuredRestDocumentation.document;
import static org.springframework.restdocs.restassured3.RestAssuredRestDocumentation.documentationConfiguration;

import com.jujeol.testtool.response.RequestResult;
import com.jujeol.testtool.response.RestAssuredResult;
import com.jujeol.testtool.util.RequestDto;
import com.jujeol.testtool.util.TestTool;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.springframework.http.HttpMethod;
import org.springframework.restdocs.RestDocumentationContextProvider;

public class RestAssuredExecutor implements TestAdapter {

    private RestDocumentationContextProvider restDocumentation;

    @Override
    public void setDocumentConfig(RestDocumentationContextProvider restDocumentation) {
        this.restDocumentation = restDocumentation;
    }

    @Override
    public boolean isAssignable(RequestDto requestDto) {
        return TestTool.REST_ASSURED.equals(requestDto.getTestTool());
    }

    @Override
    public RequestResult execute(RequestDto requestDto) {
        final RequestSpecification requestSpecification = getDocument(requestDto.getIdentifier());
        final Response response = urlRequest(requestSpecification, requestDto);
        return new RestAssuredResult(response);
    }

    private RequestSpecification getDocument(String identifier) {
        if(identifier == null || identifier.isEmpty()){
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

    private Response urlRequest(RequestSpecification requestSpecification, RequestDto requestDto) {
        final HttpMethod httpMethod = requestDto.getHttpMethod();
        if (httpMethod.matches("GET")) {
            return requestSpecification.get(requestDto.getUrl(), requestDto.getPathVariables());
        }
        if (httpMethod.matches("POST")) {
            return requestSpecification.post(requestDto.getUrl(), requestDto.getPathVariables());
        }
        if (httpMethod.matches("PUT")) {
            return requestSpecification.put(requestDto.getUrl(), requestDto.getPathVariables());
        }
        if (httpMethod.matches("DELETE")) {
            return requestSpecification.delete(requestDto.getUrl(), requestDto.getPathVariables());
        }
        return null;
    }
}
