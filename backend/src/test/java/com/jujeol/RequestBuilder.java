package com.jujeol;

import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessRequest;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.restassured3.RestAssuredRestDocumentation.document;
import static org.springframework.restdocs.restassured3.RestAssuredRestDocumentation.documentationConfiguration;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jujeol.commons.dto.CommonResponseDto;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import org.springframework.restdocs.RestDocumentationContextProvider;

public class RequestBuilder {

    private final RestDocumentationContextProvider restDocumentation;
    private final ObjectMapper objectMapper;
    private final String accessToken;

    public RequestBuilder(RestDocumentationContextProvider restDocumentation, String accessToken) {
        this.restDocumentation = restDocumentation;
        this.objectMapper = new ObjectMapper();
        this.accessToken = accessToken;
    }

    public Function builder() {
        return new Function();
    }

    public class Function {

        public Option get(String path, Object... pathParams) {
            return new Option(new GetRequest(path, pathParams));
        }

        public <T> Option post(String path, T data) {
            return new Option(new PostRequest<>(path, data));
        }

        public <T> Option put(String path, T data) {
            return new Option(new PutRequest<>(path, data));
        }

        public <T> Option delete(String path) {
            return new Option(new DeleteRequest<>(path));
        }
    }

    public class Option {

        private final RestAssuredRequest request;
        private boolean logFlag;
        private boolean withUserFlag;
        private DocumentConfig documentConfig;

        public Option(RestAssuredRequest request) {
            this.request = request;
            this.logFlag = true;
            this.withUserFlag = false;
            this.documentConfig = new DocumentConfig();
        }

        public Option withDocument(String identifier) {
            documentConfig.createDocument(identifier);
            return this;
        }

        public Option withoutLog() {
            this.logFlag = false;
            return this;
        }

        public Option withUser() {
            this.withUserFlag = true;
            return this;
        }

        public HttpResponse build() {
            RequestSpecification requestSpec;
            if (documentConfig.documentFlag) {
                requestSpec = requestWithDocument();
            } else {
                requestSpec = RestAssured.given();
            }

            if (withUserFlag) {
                requestSpec.header("Authorization", "Bearer " + accessToken);
            }

            if (logFlag) {
                requestSpec = requestSpec.log().all();
            }

            ValidatableResponse validatableResponse = request.doAction(requestSpec);

            if (logFlag) {
                validatableResponse = validatableResponse.log().all();
            }

            return new HttpResponse(validatableResponse.extract());
        }

        private RequestSpecification requestWithDocument() {
            final RequestSpecification spec = new RequestSpecBuilder()
                    .addFilter(documentationConfiguration(restDocumentation))
                    .build();

            return RestAssured.given(spec)
                    .filter(document(documentConfig.identifier,
                            preprocessRequest(prettyPrint()),
                            preprocessResponse(prettyPrint())));
        }

        private class DocumentConfig {

            private boolean documentFlag;
            private String identifier;

            public DocumentConfig() {
                this.documentFlag = false;
            }

            void createDocument(String identifier) {
                this.identifier = identifier;
                this.documentFlag = true;
            }
        }
    }

    public class HttpResponse {

        private final ExtractableResponse<Response> extractableResponse;

        public HttpResponse(ExtractableResponse<Response> extractableResponse) {
            this.extractableResponse = extractableResponse;
        }

        public <T> T convertBody(Class<T> tClass) {
            final CommonResponseDto responseDto
                    = extractableResponse.body().as(CommonResponseDto.class);

            final LinkedHashMap data = (LinkedHashMap) responseDto.getData();
            return objectMapper.convertValue(data, tClass);
        }

        public <T> List<T> convertBodyToList(Class<T> tClass) {
            final String json = extractableResponse.asString();
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

        public ExtractableResponse<Response> totalResponse() {
            return extractableResponse;
        }
    }

    interface RestAssuredRequest {

        ValidatableResponse doAction(RequestSpecification spec);
    }

    private static class GetRequest implements RestAssuredRequest {

        private final String path;
        private final Object[] pathParams;

        public GetRequest(String path, Object[] pathParams) {
            this.path = path;
            this.pathParams = pathParams;
        }

        @Override
        public ValidatableResponse doAction(RequestSpecification spec) {
            return spec.get(path, pathParams)
                    .then();
        }
    }

    private static class PostRequest<T> implements RestAssuredRequest {

        private final String path;
        private final T data;

        public PostRequest(String path, T data) {
            this.path = path;
            this.data = data;
        }

        @Override
        public ValidatableResponse doAction(RequestSpecification spec) {
            return spec.body(data).contentType(ContentType.JSON)
                    .post(path)
                    .then();
        }
    }

    private static class PutRequest<T> implements RestAssuredRequest {

        private final String path;
        private final T data;

        public PutRequest(String path, T data) {
            this.path = path;
            this.data = data;
        }

        @Override
        public ValidatableResponse doAction(RequestSpecification spec) {
            return spec.body(data).contentType(ContentType.JSON)
                    .put(path)
                    .then();
        }
    }

    private static class DeleteRequest<T> implements RestAssuredRequest {

        private final String path;

        public DeleteRequest(String path) {
            this.path = path;
        }

        @Override
        public ValidatableResponse doAction(RequestSpecification spec) {
            return spec.contentType(ContentType.JSON)
                    .delete(path)
                    .then();
        }
    }
}
