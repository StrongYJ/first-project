package com.greenart.firstproject.service;

public interface JwtService {
    public String getToken(String key, Object value);
}
