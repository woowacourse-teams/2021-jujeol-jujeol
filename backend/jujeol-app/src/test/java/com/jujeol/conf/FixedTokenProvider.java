package com.jujeol.conf;

import com.jujeol.member.domain.exception.TokenExpiredException;
import com.jujeol.member.domain.model.TokenProvider;
import org.springframework.stereotype.Component;

@Component
public class FixedTokenProvider implements TokenProvider {

    private String payload;
    private String token;

    @Override
    public String createToken(String payload) {
        this.payload = payload;
        return token;
    }

    @Override
    public String getPayload(String token) {
        return payload;
    }

    @Override
    public boolean validateToken(String token) throws TokenExpiredException {
        return token.equals(this.token);
    }

    public void fixToken(String token) {
        this.token = token;
    }

    public void reset() {
        this.payload = "";
        this.token = "";
    }
}
