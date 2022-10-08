package com.springboot.demo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.context.SecurityContextPersistenceFilter;
import org.springframework.web.filter.CorsFilter;

import com.springboot.demo.config.jwt.JwtAuthenticationFilter;
import com.springboot.demo.filter.FirstFilter;
import com.springboot.demo.filter.MyFilter;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    
    private final CorsFilter corsFilter;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // http.addFilterAfter(new MyFilter(),BasicAuthenticationFilter.class);
        http.addFilterBefore(new FirstFilter(), SecurityContextPersistenceFilter.class);
        http.csrf().disable();
        //세션관리를 하지않겠다.
        //bearer방식을 쓰려면 stateless, formLogin disable, httpBasic disable을 해줘야한다.
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        .and()
        .addFilter(corsFilter)//@CrossOrign(인증x경우에만 가능)
        .formLogin().disable()//form tag로그인을 안함.
        .httpBasic().disable()
        .addFilter(new JwtAuthenticationFilter(authenticationManager()))//AuthenticationManager를 파라메터로 필수로 보내줘야함.
        .authorizeRequests()
        .antMatchers("/api/vi/user/**")
        .access("hasRole('ROLE_MANAGER') or hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
        .antMatchers("/api/vi/manager/**")
        .access("hasRole('ROLE_USER') or hasRole('ROLE_MANAGER')")
        .antMatchers("/api/vi/admin/**")
        .access("hasRole('ROLE_ADMIN')")
        .anyRequest().permitAll();
    }
}
