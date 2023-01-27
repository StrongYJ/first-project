package com.greenart.firstproject.service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.spec.SecretKeySpec;

import org.springframework.stereotype.Component;

import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.xml.bind.DatatypeConverter;

@Component
public class JwtServiceImpl implements JwtService{

    private String secretKey = "diqklcn123145boia#@#$$;olfklgnoh124354l5ty@!@#$!@$#T@^9y8oalkd)(*_()*nklf";

    @Override
    public String getToken(String key, Object value) {
        
        Date expTime = new Date();
        expTime.setTime(expTime.getTime() * 1000 * 60 * 5);
        byte[] secretByteKey = DatatypeConverter.parseBase64Binary(secretKey);
        Key signKey = new SecretKeySpec(secretByteKey, SignatureAlgorithm.HS256.getJcaName());

        Map<String, Object> headerMap = new HashMap<>();
        headerMap.put("typ", "JWT");
        headerMap.put("alg", "HS256");

        Map<String, Object> map = new HashMap<>();
        map.put(key, value);

        JwtBuilder builder = Jwts.builder().setHeader(headerMap)
            .setClaims(map)
            .setExpiration(expTime)
            .signWith(signKey, SignatureAlgorithm.HS256);

        return builder.compact();
    }
    
}
