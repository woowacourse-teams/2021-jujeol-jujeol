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
import com.jujeol.member.auth.domain.ProviderName;
import com.jujeol.member.fixture.TestMember;
import com.jujeol.testdatabase.QueryCounter;
import com.jujeol.testdatabase.QueryResult;
import io.restassured.RestAssured;
import io.restassured.builder.MultiPartSpecBuilder;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.MultiPartSpecification;
import io.restassured.specification.RequestSpecification;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import org.apache.commons.io.Charsets;
import org.springframework.http.HttpStatus;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.stereotype.Component;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.multipart.MultipartFile;

@Component
@ActiveProfiles("test")
public class RequestBuilder {

    private final ObjectMapper objectMapper;
    private final LoginService loginService;
    private final QueryCounter queryCounter;

    private RestDocumentationContextProvider restDocumentation;

    public RequestBuilder(ObjectMapper objectMapper, LoginService loginService,
        QueryCounter queryCounter) {
        this.objectMapper = objectMapper;
        this.loginService = loginService;
        this.queryCounter = queryCounter;
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

        public <T> Option postWithoutData(String path, Object... pathParams) {
            return new Option(new PostRequest<>(path, pathParams));
        }

        public <T> Option put(String path, T data, Object... pathParams) {
            return new Option(new PutRequest<>(path, data, pathParams));
        }

        public <T> Option putWithoutData(String path, Object... pathParams) {
            return new Option(new PutRequest<>(path, pathParams));
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
        private MultipartHelper multipartHelper;

        public Option(RestAssuredRequest request) {
            this.request = request;
            this.logFlag = true;
            this.documentHelper = new DocumentHelper();
            this.userHelper = new UserHelper();
            this.multipartHelper = new MultipartHelper();
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

        public Option addMultipart(String name, String contentBody) {
            multipartHelper.addMultipart(name, contentBody);
            return this;
        }

        public Option addMultipart(String name, Double contentBody) {
            multipartHelper.addMultipart(name, contentBody);
            return this;
        }

        public Option addMultipart(String name, File contentBody) {
            multipartHelper.addMultipart(name, contentBody);
            return this;
        }

        public Option addMultipart(String name, MultipartFile multipartFile) {
            multipartHelper.addMultipart(name, toFile(multipartFile));
            return this;
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

        public HttpResponse build() {
            RequestSpecification requestSpec = documentHelper.startRequest();

            userHelper.addRequest(requestSpec);

            multipartHelper.addRequest(requestSpec);

            if (logFlag) {
                requestSpec = requestSpec.log().all();
            }

            queryCounter.startCount();
            ValidatableResponse validatableResponse = request.doAction(requestSpec);
            final QueryResult queryResult = queryCounter.endCount();

            if (logFlag) {
                validatableResponse = validatableResponse.log().all();
                queryResult.printLog();
            }

            return new HttpResponse(validatableResponse.extract(), queryResult);
        }

        private class MultipartHelper {

            private boolean multipartFlag;

            private final List<MultiPartSpecification> multiPartSpecifications = new ArrayList<>();

            public void addRequest(RequestSpecification requestSpec) {
                if (multipartFlag) {
                    requestSpec.contentType("multipart/form-data");
                    multiPartSpecifications.forEach(requestSpec::multiPart);
                }
            }

            public void addMultipart(String name, String contentBody) {
                multipartFlag = true;
                multiPartSpecifications.add(
                    new MultiPartSpecBuilder(contentBody)
                        .controlName(name)
                        .charset(Charsets.UTF_8)
                        .build()
                );
            }

            public void addMultipart(String name, Double contentBody) {
                multipartFlag = true;
                multiPartSpecifications.add(
                    new MultiPartSpecBuilder(contentBody)
                        .controlName(name)
                        .charset(Charsets.UTF_8)
                        .build()
                );
            }

            public void addMultipart(String name, File contentBody) {
                multipartFlag = true;
                multiPartSpecifications.add(
                    new MultiPartSpecBuilder(contentBody)
                        .controlName(name)
                        .build()
                );
            }
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
                token = loginService
                    .createToken(SocialProviderCodeDto.create(testMember.getMatchedCode(),
                        ProviderName.TEST)).getAccessToken();
                withUser(token);
            }

            public void withUser(String token) {
                userFlag = true;
                this.token = token;
            }

            public void addRequest(RequestSpecification requestSpec) {
                if (userFlag) {
                    requestSpec.header("Authorization", "Bearer " + token);
                }
            }
        }
    }

    public class HttpResponse {

        private final ExtractableResponse<Response> extractableResponse;
        private final QueryResult queryResult;

        public HttpResponse(ExtractableResponse<Response> extractableResponse,
            QueryResult queryResult) {
            this.extractableResponse = extractableResponse;
            this.queryResult = queryResult;
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

        public Long queryCount() {
            return queryResult.queryCount();
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
        private T data;
        private final Object[] pathParams;

        public PostRequest(String path, T data, Object[] pathParams) {
            this.path = path;
            this.data = data;
            this.pathParams = pathParams;
        }

        public PostRequest(String path, Object[] pathParams) {
            this.path = path;
            this.pathParams = pathParams;
        }

        @Override
        public ValidatableResponse doAction(RequestSpecification spec) {
            if (data != null) {
                spec.body(data).contentType(ContentType.JSON);
            }
            return spec
                .post(path, pathParams)
                .then();
        }
    }

    private static class PutRequest<T> implements RestAssuredRequest {

        private final String path;
        private T data;
        private final Object[] pathParams;

        public PutRequest(String path, T data, Object[] pathParams) {
            this.path = path;
            this.data = data;
            this.pathParams = pathParams;
        }

        public PutRequest(String path, Object[] pathParams) {
            this.path = path;
            this.pathParams = pathParams;
        }

        @Override
        public ValidatableResponse doAction(RequestSpecification spec) {
            if (data != null) {
                spec.body(data).contentType(ContentType.JSON);
            }
            return spec
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
