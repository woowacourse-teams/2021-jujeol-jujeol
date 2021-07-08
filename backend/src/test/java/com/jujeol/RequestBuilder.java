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

    public Given builder() {
        return new Given();
    }

    public class Given {

        private final RequestSpecification spec;

        public Given() {
            this.spec = new RequestSpecBuilder()
                    .addFilter(documentationConfiguration(restDocumentation))
                    .build();
        }

        public When withDocument(String identifier) {
            return new When(RestAssured
                    .given(spec)
                    .filter(document(identifier, preprocessRequest(prettyPrint()),
                            preprocessResponse(prettyPrint()))));
        }

        public When noDocument() {
            return new When(RestAssured.given());
        }
    }

    public class When {

        private final RequestSpecification requestSpecification;

        public When(RequestSpecification requestSpecification) {
            this.requestSpecification = requestSpecification;
        }

        public Then get(String path, Object... pathParams) {
            return new Then(requestSpecification, new GetRequest(path, pathParams));
        }

        public <T> Then post(String path, T data) {
            return new Then(requestSpecification, new PostRequest<>(path, data));
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

    public class Then {

        private RequestSpecification requestSpecification;
        private RestAssuredRequest restAssuredRequest;
        private boolean logFlag = true;

        public Then(RequestSpecification requestSpecification,
                RestAssuredRequest restAssuredRequest) {
            this.requestSpecification = requestSpecification;
            this.restAssuredRequest = restAssuredRequest;
        }

        public Then withoutLog() {
            this.logFlag = false;
            return this;
        }

        public ExtractableResponse<Response> build() {
            if(logFlag) {
                this.requestSpecification = requestSpecification.log().all();
            }

            ValidatableResponse validatableResponse =
                    restAssuredRequest
                            .doAction(requestSpecification);

            if(logFlag) {
                validatableResponse = validatableResponse.log().all();
            }

            return validatableResponse.extract();
        }
    }
}
