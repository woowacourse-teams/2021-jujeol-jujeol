package com.jujeol.member.application.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class TokenResponse {

    private String accessToken;

    public static TokenResponse from(String accessToken) {
        return new TokenResponse(accessToken);
    }
}
