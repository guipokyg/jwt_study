package com.springboot.demo.config.jwt;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import lombok.RequiredArgsConstructor;

//스프링 시큐리티에서 UsernamePasswordAuthenticationFilter 필터가 있음.
//원래는 login이라고 요청해서 username,password 전송하면(post)  필터가 동작함.
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private final AuthenticationManager authenticationManager;

    //로그인시도때 실행되는함수
    // /login 요청을하면 실행되는 함수
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException {
       System.out.println("##### login 시도");

       //username, password 받아서
       // /login실행을하면 authenticationManager 로그인시도를 하면 > principalDetailsService가 실행됨.
        return super.attemptAuthentication(request, response);
    }
}
