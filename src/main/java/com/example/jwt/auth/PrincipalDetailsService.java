package com.example.jwt.auth;

import com.example.jwt.model.User;
import com.example.jwt.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


    // http://localhost:8080/login 요청이 올때 동작한다.(스프링 시큐리티 기본 주소)
    @Service
    @RequiredArgsConstructor
    public class PrincipalDetailsService implements UserDetailsService{

        private final UserRepository userRepository;

        // 회원 아이디와 같은 식별 값으로 회원 정보를 가져온다.
        @Override
        public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException{
            User userEntity = userRepository.findByUsername(username);
            System.out.println(userEntity);
            return new PrincipalDetails(userEntity);
        }
    }

