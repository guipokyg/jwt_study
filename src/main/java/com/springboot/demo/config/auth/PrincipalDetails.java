package com.springboot.demo.config.auth;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.springboot.demo.model.User;

import lombok.Data;


//security Session => Authentication => UserDetails
@Data
public class PrincipalDetails implements UserDetails {

    private User user;
    
    public PrincipalDetails(User user){
        this.user = user;
    }
    
    //해당유저의 권한을 리턴
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
       Collection<GrantedAuthority> authorities = new ArrayList<>();
       user.getRoleList().forEach(r ->{
        System.out.println(r);
        //authorities.add(new SimpleGrantedAuthority(authority));
        authorities.add(()-> r);
        //authorities.add(r);
       });

        return authorities;
    }

    @Override
    public String getPassword() {
        // TODO Auto-generated method stub
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        // TODO Auto-generated method stub
        return user.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        // TODO Auto-generated method stub
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        // TODO Auto-generated method stub
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        // TODO Auto-generated method stub
        return true;
    }

    @Override
    public boolean isEnabled() {
        // TODO Auto-generated method stub
        return true;
    }
}
