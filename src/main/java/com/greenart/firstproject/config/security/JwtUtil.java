package com.greenart.firstproject.config.security;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

@Component
public class JwtUtil {

    @Value("${jwt.key}")
    private String key;

    public String create(Long userSeq) {
        return JWT.create()
            .withExpiresAt(new Date(System.currentTimeMillis() + JwtProperties.EXPIRATION_TIME))
            .withClaim("userSeq", userSeq)
            .sign(Algorithm.HMAC256(key));
    }

    public Long verifyAndExtractClaim(String token) {
        return JWT.require(Algorithm.HMAC256(key)).build().verify(token).getClaim("userSeq").asLong();
    }

    public String resolve(String token) {
        return token.replace(JwtProperties.TOKEN_PREFIX, "");
    }
}
