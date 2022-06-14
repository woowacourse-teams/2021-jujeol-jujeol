package com.jujeol.member.domain.model;

import lombok.Getter;

@Getter
public class Token {

    private final String accessToken;

    private Token(String accessToken) {
        this.accessToken = accessToken;
    }

    public static Token create(String accessToken) {
        return new Token(accessToken);
    }
}
