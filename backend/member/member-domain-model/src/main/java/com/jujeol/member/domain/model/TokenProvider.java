package com.jujeol.member.domain.model;

import com.jujeol.member.domain.exception.TokenExpiredException;

public interface TokenProvider {

    String createToken(String payload);

    String getPayload(String token);

    boolean validateToken(String token) throws TokenExpiredException;
}
