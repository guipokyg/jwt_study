package com.springboot.demo.config.jwt;

import java.io.IOException;
import java.util.Date;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.springboot.demo.config.auth.PrincipalDetails;
import com.springboot.demo.model.User;

import lombok.RequiredArgsConstructor;

//스프링 시큐리티에서 UsernamePasswordAuthenticationFilter 필터가 있음.
//원래는 login이라고 요청해서 username,password 전송하면(post)  필터가 동작함.
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private final AuthenticationManager authenticationManager;
     
    //로그인시도때 실행되는함수
    // /login 요청을하면 실행되는 함수
    /* (non-Javadoc)
     * @see org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter#attemptAuthentication(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
     */
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException {
       //username, password 받아서
       // /login실행을하면 authenticationManager 로그인시도를 하면 > principalDetailsService가 실행됨.
       System.out.println("##### login 시도");


        // 파라메터 json 타입 처리
        ObjectMapper om = new ObjectMapper();
        try {
             User user = om.readValue(request.getInputStream(),User.class);
            System.out.println(user);
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken("admin", "1234");
        
            //pricipaldetails service의 loadUerbyUsernmae함수가 실행됨.
            //username과 password를 일치 확인
            Authentication authentication = authenticationManager.authenticate(authenticationToken);
            
            //authentication 객체가 session 영역에 저장됨 
            //jwt토큰을 사용하면서 세션을 만들이유는 없다..
            return authentication;
           // return super.attemptAuthentication(request,response);
        }  catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    //로그인인증이 되면 아래메소드 호출됨.
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
            Authentication authResult) throws IOException, ServletException {
        System.out.println("sucessAuthentication 실행됨.");
        PrincipalDetails principalDetails = (PrincipalDetails) authResult.getPrincipal();
        
        String jwtToken = JWT.create()
                        .withSubject("COMPL JWT")
                        .withExpiresAt(new Date(System.currentTimeMillis() + (1000*60*10)))
                        .withClaim("id",principalDetails.getUsername())
                        .withClaim("username",principalDetails.getUsername())
                        .sign(Algorithm.HMAC512("COMPL"));

        response.addHeader("Authorization","Bearer "+jwtToken);
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
            AuthenticationException failed) throws IOException, ServletException {
        super.unsuccessfulAuthentication(request, response, failed);
    }
}
