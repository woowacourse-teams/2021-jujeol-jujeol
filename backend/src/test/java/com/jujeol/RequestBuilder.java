package com.jujeol;

import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessRequest;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.restassured3.RestAssuredRestDocumentation.document;
import static org.springframework.restdocs.restassured3.RestAssuredRestDocumentation.documentationConfiguration;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import org.springframework.restdocs.RestDocumentationContextProvider;

public class RequestBuilder {

    private final RestDocumentationContextProvider restDocumentation;

    public RequestBuilder(RestDocumentationContextProvider restDocumentation) {
        this.restDocumentation = restDocumentation;
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
    }

    public class Option {

        private final RestAssuredRequest request;
        private boolean logFlag;
        private DocumentConfig documentConfig;

        public Option(RestAssuredRequest request) {
            this.request = request;
            this.logFlag = true;
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

        public ExtractableResponse<Response> build() {
            RequestSpecification requestSpec;
            if (documentConfig.documentFlag) {
                requestSpec = requestWithDocument();
            } else {
                requestSpec = RestAssured.given();
            }

            if(logFlag) {
                requestSpec = requestSpec.log().all();
            }

            ValidatableResponse validatableResponse = request.doAction(requestSpec);

            if (logFlag) {
                validatableResponse = validatableResponse.log().all();
            }

            return validatableResponse.extract();
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
}
