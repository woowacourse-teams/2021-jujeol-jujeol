package com.jujeol.commons.interceptor;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class RequestPath {

    private String pathPattern;
    private PathMethod pathMethod;

    public boolean matchesMethod(String pathMethod) {
        return this.pathMethod.isAny() || this.pathMethod.matches(pathMethod);
    }
}
