package com.greenart.firstproject.config.security;

public interface JwtProperties {
    long EXPIRATION_TIME = 1000 * 60 * 30;
    String TOKEN_PREFIX = "Bearer ";
}
