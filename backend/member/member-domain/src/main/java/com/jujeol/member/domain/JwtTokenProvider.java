package com.jujeol.member.domain;

import com.jujeol.member.domain.exception.TokenExpiredException;
import com.jujeol.member.domain.model.TokenProvider;
import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Value;

import java.util.Date;

// TODO : module 모듈에 넣을 지 "도메인-모델"에 넣을 지 의논 필요
public class JwtTokenProvider implements TokenProvider {

    @Value("${security.jwt.token.secret-key:1234}")
    private String secretKey;
    @Value("${security.jwt.token.expire-length:200000}")
    private long validityInMilliseconds;

    public String createToken(String payload) {
        Claims claims = Jwts.claims().setSubject(payload);
        Date now = new Date();
        Date validity = new Date(now.getTime() + validityInMilliseconds);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(validity)
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }

    public String getPayload(String token) {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject();
    }

    public boolean validateToken(String token) {
        try {
            Jws<Claims> claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);

            if(claims.getBody().getExpiration().before(new Date())) {
               throw new TokenExpiredException();
            }

            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }
}
