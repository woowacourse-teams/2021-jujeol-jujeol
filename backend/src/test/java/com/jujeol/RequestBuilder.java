package com.jujeol;

import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessRequest;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.restassured3.RestAssuredRestDocumentation.document;
import static org.springframework.restdocs.restassured3.RestAssuredRestDocumentation.documentationConfiguration;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jujeol.commons.dto.CommonResponse;
import com.jujeol.commons.dto.PageInfo;
import com.jujeol.commons.exception.JujeolExceptionDto;
import com.jujeol.member.auth.application.LoginService;
import com.jujeol.member.auth.application.dto.SocialProviderCodeDto;
import com.jujeol.member.auth.application.dto.TokenDto;
import com.jujeol.member.auth.domain.ProviderName;
import com.jujeol.member.fixture.TestMember;
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
import org.springframework.http.HttpStatus;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.stereotype.Component;
import org.springframework.test.context.ActiveProfiles;

@Component
@ActiveProfiles("test")
public class RequestBuilder {

    private final ObjectMapper objectMapper;
    private final LoginService loginService;

    private RestDocumentationContextProvider restDocumentation;

    public RequestBuilder(ObjectMapper objectMapper, LoginService loginService) {
        this.objectMapper = objectMapper;
        this.loginService = loginService;
    }

    public void setRestDocumentation(RestDocumentationContextProvider restDocumentation) {
        this.restDocumentation = restDocumentation;
    }

    public Function builder() {
        return new Function();
    }

    public class Function {

        public Option get(String path, Object... pathParams) {
            return new Option(new GetRequest(path, pathParams));
        }

        public <T> Option post(String path, T data, Object... pathParams) {
            return new Option(new PostRequest<>(path, data, pathParams));
        }

        public <T> Option put(String path, T data, Object... pathParams) {
            return new Option(new PutRequest<>(path, data, pathParams));
        }

        public <T> Option delete(String path, Object... pathParams) {
            return new Option(new DeleteRequest<>(path, pathParams));
        }
    }

    public class Option {

        private final RestAssuredRequest request;
        private boolean logFlag;
        private DocumentHelper documentHelper;
        private UserHelper userHelper;

        public Option(RestAssuredRequest request) {
            this.request = request;
            this.logFlag = true;
            this.documentHelper = new DocumentHelper();
            this.userHelper = new UserHelper();
        }

        public Option withDocument(String identifier) {
            documentHelper.createDocument(identifier);
            return this;
        }

        public Option withoutLog() {
            this.logFlag = false;
            return this;
        }

        public Option withUser() {
            userHelper.withUser(TestMember.RANDOM_MEMBER);
            return this;
        }

        public Option withUser(TestMember testMember) {
            userHelper.withUser(testMember);
            return this;
        }

        public Option withUser(String token) {
            userHelper.withUser(token);
            return this;
        }

        public HttpResponse build() {
            RequestSpecification requestSpec = documentHelper.startRequest();
            userHelper.addRequest(requestSpec);

            if (logFlag) {
                requestSpec = requestSpec.log().all();
            }

            ValidatableResponse validatableResponse = request.doAction(requestSpec);

            if (logFlag) {
                validatableResponse = validatableResponse.log().all();
            }

            return new HttpResponse(validatableResponse.extract());
        }

        private class DocumentHelper {

            private boolean documentFlag;
            private String identifier;

            public DocumentHelper() {
                this.documentFlag = false;
            }

            void createDocument(String identifier) {
                this.identifier = identifier;
                this.documentFlag = true;
            }

            public RequestSpecification startRequest() {
                if (documentHelper.documentFlag) {
                    return requestWithDocument();
                }

                return RestAssured.given();
            }

            private RequestSpecification requestWithDocument() {
                final RequestSpecification spec = new RequestSpecBuilder()
                        .addFilter(documentationConfiguration(restDocumentation))
                        .build();

                return RestAssured.given(spec)
                        .filter(document(documentHelper.identifier,
                                preprocessRequest(prettyPrint()),
                                preprocessResponse(prettyPrint())));
            }
        }

    private class UserHelper {
            private boolean userFlag;
            private String token;

            public UserHelper() {
                this.userFlag = false;
            }

             public void withUser(TestMember testMember) {
                token = loginService.createToken(SocialProviderCodeDto.create(testMember.getMatchedCode(), 
                                   ProviderName.TEST)).getAccessToken();
                withUser(token);
            }

            public void withUser(String token) {
                userFlag = true;
                this.token = token;
            }

            public void addRequest(RequestSpecification requestSpec) {
                if(userFlag) {
                    requestSpec.header("Authorization", "Bearer " + token);
                }
            }
        }
    }

    public class HttpResponse {

        private final ExtractableResponse<Response> extractableResponse;

        public HttpResponse(ExtractableResponse<Response> extractableResponse) {
            this.extractableResponse = extractableResponse;
        }

        public <T> T convertBody(Class<T> tClass) {
            final CommonResponse responseDto
                    = extractableResponse.body().as(CommonResponse.class);

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

        public HttpStatus statusCode() {
            return HttpStatus.valueOf(extractableResponse.statusCode());
        }

        public JujeolExceptionDto errorResponse() {
            return extractableResponse.as(JujeolExceptionDto.class);
        }

        public PageInfo pageInfo() {
            return extractableResponse.body().as(CommonResponse.class).getPageInfo();
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
        private final Object[] pathParams;

        public PostRequest(String path, T data, Object[] pathParams) {
            this.path = path;
            this.data = data;
            this.pathParams = pathParams;
        }

        @Override
        public ValidatableResponse doAction(RequestSpecification spec) {
            return spec.body(data).contentType(ContentType.JSON)
                    .post(path, pathParams)
                    .then();
        }
    }

    private static class PutRequest<T> implements RestAssuredRequest {

        private final String path;
        private final T data;
        private final Object[] pathParams;

        public PutRequest(String path, T data, Object[] pathParams) {
            this.path = path;
            this.data = data;
            this.pathParams = pathParams;
        }

        @Override
        public ValidatableResponse doAction(RequestSpecification spec) {
            return spec.body(data).contentType(ContentType.JSON)
                    .put(path, pathParams)
                    .then();
        }
    }

    private static class DeleteRequest<T> implements RestAssuredRequest {

        private final String path;
        private final Object[] pathParams;

        public DeleteRequest(String path, Object[] pathParams) {
            this.path = path;
            this.pathParams = pathParams;
        }

        @Override
        public ValidatableResponse doAction(RequestSpecification spec) {
            return spec.contentType(ContentType.JSON)
                    .delete(path, pathParams)
                    .then();
        }
    }
}
