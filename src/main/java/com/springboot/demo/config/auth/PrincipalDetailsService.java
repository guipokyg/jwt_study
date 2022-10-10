package com.springboot.demo.config.auth;


import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.springboot.demo.model.User;

import lombok.RequiredArgsConstructor;

//http://localhost:8080/login 요청이 올때 동작함. => 근데 동작을안함(forlogin disable). 그래서 필터를 만들어야함
@Service
@RequiredArgsConstructor
public class PrincipalDetailsService implements UserDetailsService  {
    
    //private final userrepository
    private final BCryptPasswordEncoder bcryptPasswordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println("principalDetailService ::: ");
        User user = new User();
        user.setUsername("admin");
        user.setPassword(bcryptPasswordEncoder.encode("1234"));
        user.setRoles("ROLE_USER");
        System.out.println(user);
        return new PrincipalDetails(user);
    }
    
}
