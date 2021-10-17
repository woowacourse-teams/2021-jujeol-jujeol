package com.jujeol.testtool.util;

import com.jujeol.member.fixture.TestMember;
import java.util.HashMap;
import java.util.Map;
import org.springframework.http.HttpMethod;

public class RequestDto {

    private final HttpMethod httpMethod;
    private final String url;
    private final TestTool testTool;
    private final Object data;
    private final Object[] pathVariables;
    private String identifier;
    private TestMember testMember;
    private String token;
    private boolean logFlag = true;
    private Map<String, Object> formData = new HashMap<>();

    public RequestDto(
            HttpMethod httpMethod,
            String url,
            TestTool testTool,
            Object data,
            Object... pathVariables
    ) {
        this.httpMethod = httpMethod;
        this.url = url;
        this.testTool = testTool;
        this.data = data;
        this.pathVariables = pathVariables;
    }

    public HttpMethod getHttpMethod() {
        return httpMethod;
    }

    public String getUrl() {
        return url;
    }

    public TestTool getTestTool() {
        return testTool;
    }

    public Object getData() {
        return data;
    }

    public Object[] getPathVariables() {
        return pathVariables;
    }

    public String getIdentifier() {
        return identifier;
    }

    public TestMember getTestMember() {
        return testMember;
    }

    public String getToken() {
        return token;
    }

    public Map<String, Object> getFormData() {
        return formData;
    }

    public boolean isLogFlag() {
        return logFlag;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public void setTestMember(TestMember testMember) {
        this.testMember = testMember;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public void notLogging() {
        this.logFlag = false;
    }

    public void addFormData(String name, Object contentBody) {
        formData.put(name, contentBody);
    }
}
