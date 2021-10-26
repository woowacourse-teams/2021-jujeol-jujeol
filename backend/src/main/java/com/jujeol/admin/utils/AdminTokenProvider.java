package com.jujeol.admin.utils;

import com.jujeol.member.auth.exception.TokenExpiredException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.util.Date;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class AdminTokenProvider {

    @Value("${security.jwt.admin-token.secret-key:1234}")
    private String secretKey;
    @Value("${security.jwt.admin-token.expire-length:200000}")
    private long validityInMilliseconds;

    public String createToken() {
        Claims claims = Jwts.claims();
        Date now = new Date();
        Date validity = new Date(now.getTime() + validityInMilliseconds);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(validity)
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
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
