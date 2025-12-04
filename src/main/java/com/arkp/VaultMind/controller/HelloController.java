package com.arkp.VaultMind.controller;

import com.arkp.VaultMind.service.JwtService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @Autowired
    JwtService jwtService;
    @GetMapping("hello")
    public String Hello(HttpServletRequest request){
        String token= request.getHeader("Authorization").substring(7);
        return jwtService.extractUsername(token);
    }
}
