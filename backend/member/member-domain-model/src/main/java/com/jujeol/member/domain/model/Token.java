package com.jujeol.member.domain.model;

import lombok.Getter;

@Getter
public class Token {

    private String accessToken;

    public Token(String accessToken) {
        this.accessToken = accessToken;
    }

    public static Token create(String accessToken) {
        return new Token(accessToken);
    }
}
