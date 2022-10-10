package com.springboot.demo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RestApiController {
    
    @GetMapping("home")
    public String home(){
        return "<h1>home</h1>";
    }
    @PostMapping("token")
    public String token(){
        return "<h1>token</h1>";
    }

    @PostMapping("/api/v1/user/a")
    public String user(){

        return "user";
    }
    
    @PostMapping("/api/v1/manager/a")
    public String manager(){

        return "manager";
    }
   
    @PostMapping("/api/v1/admin/a")
    public String admin(){

        return "admin";
    }
}
