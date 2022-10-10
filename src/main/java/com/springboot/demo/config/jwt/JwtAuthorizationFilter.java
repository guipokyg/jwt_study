package com.springboot.demo.config.jwt;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.springboot.demo.config.auth.PrincipalDetails;
import com.springboot.demo.model.User;

// 시큐리티 필터중 basicauthenticationFilter가 있다.
// 권한이나 인증이 필요한 특정주소를 요청했을때 위 필터를 무조건 탐.
// 만약에 권한이나 인증이 필요한 주소가 아니라면 이필터를 안탐.
public class JwtAuthorizationFilter extends BasicAuthenticationFilter {
    
    public JwtAuthorizationFilter(AuthenticationManager authenticationManager){
        super(authenticationManager);
        
    }

    //인증이나 권한이 필요한 주소요청이 있으면 해당 필터를 타게됨.
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        
        System.out.println("######  인증이나 권한이 필요한 주소인가");

        String jwtHeader = request.getHeader("Authorization");
        System.out.println("jwt header : " + jwtHeader);

        //header가 있는지 확인;
        if(jwtHeader == null || !jwtHeader.startsWith("Bearer")){
            chain.doFilter(request,response);
            return;
        }
        String username = "";
        String jwtToken = request.getHeader("Authorization").replace("Bearer ","");
        
        try{
            username = JWT.require(Algorithm.HMAC512("COMPL")).build().verify(jwtToken).getClaim("username").asString();
        }catch(TokenExpiredException ete ){
            ete.printStackTrace();
            // throw ete;
        }

        System.out.println("username :: " + username);
        if(username != null){
            if(username.equals("admin")){
                User user = new User();
                user.setUsername("admin");
                user.setPassword(new BCryptPasswordEncoder().encode("1234"));
                user.setRoles("ROLE_USER");
                PrincipalDetails principalDetails = new PrincipalDetails(user);
                
                //jwt 토큰 서명을 통해 정상이면 authentication 객체를 만들어줌. 
                Authentication authentication = new UsernamePasswordAuthenticationToken(principalDetails, null,principalDetails.getAuthorities());

                //강제로 시큐리티의 세션에 접근하여 Authentication 객체를 저장
                SecurityContextHolder.getContext().setAuthentication(authentication);

            }
            chain.doFilter(request, response);
        }
    }
}
