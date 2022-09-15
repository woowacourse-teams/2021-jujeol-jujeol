package com.jujeol.member.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.util.HashMap;
import java.util.Map;

public class AuthHttpServletRequest extends HttpServletRequestWrapper {

    private final Map<String, String> customHeaders = new HashMap<>();

    public AuthHttpServletRequest(HttpServletRequest request) {
        super(request);
    }

    public void putHeader(String name, String value){
        this.customHeaders.put(name, value);
    }

    @Override
    public String getHeader(String name) {
        return customHeaders.getOrDefault(name, ((HttpServletRequest) getRequest()).getHeader(name));
    }
}
