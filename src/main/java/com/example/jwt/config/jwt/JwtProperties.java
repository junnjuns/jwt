package com.example.jwt.config.jwt;

public interface JwtProperties {
    String SECRET ="cos"; //서버만 알고있는 비밀값
    int EXPIRATION_TIME = 60000*10;
    String TOKEN_PREFIX ="Bearer ";
    String HEADER_STRING ="Authorization";
}
