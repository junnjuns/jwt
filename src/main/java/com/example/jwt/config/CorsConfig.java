package com.example.jwt.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;


@Configuration
public class CorsConfig {

    @Bean
    public CorsFilter corsFilter(){
        UrlBasedCorsConfigurationSource source =new UrlBasedCorsConfigurationSource();
        CorsConfiguration config =new CorsConfiguration();
        config.setAllowCredentials(true);      //내 새버가 응답을 할때 json을 자바 스크립트에서 처리할 수 있게 할지 설정하는 것
        config.addAllowedOrigin("*");   //모든 ip에 응답을 허용
        config.addAllowedHeader("*");   //모든 header에 응답을 허용
        config.addAllowedMethod("*");   //모든 post,get,put,delete,patch 요청을 허용

        source.registerCorsConfiguration("/api/**", config);    // /api/로 들어오는 모든 주소는 config 설정 따라하게 한다.
        return new CorsFilter(source);
    }
}
