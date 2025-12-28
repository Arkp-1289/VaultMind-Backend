package com.arkp.VaultMind.controller;


import com.arkp.VaultMind.dto.auth.LoginRequest;
import com.arkp.VaultMind.dto.auth.RegisterRequest;
import com.arkp.VaultMind.model.User;
import com.arkp.VaultMind.service.AuthService;
import com.arkp.VaultMind.service.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/user")
public class AuthController {

    @Autowired
    AuthService authService;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    JwtService jwtService;


    @PostMapping("register")
    public ResponseEntity<String> userRegister(@RequestBody RegisterRequest registerRequest){
        if (!registerRequest.getPassword().equals(registerRequest.getConfirmPassword())){
            return new ResponseEntity<>("Both Passwords should match", HttpStatus.UNAUTHORIZED);
        }

        User user1= authService.userRegister(registerRequest);

        if (user1==null){
          return  new ResponseEntity<>("UserId  already exists",HttpStatus.UNAUTHORIZED);
        }
        return new ResponseEntity<>("Account created Successfully",HttpStatus.OK);
    }

    @PostMapping("login")
    public ResponseEntity<?> userLogin(@RequestBody LoginRequest user){
//      return authService.userLogin(user);
        System.out.println("login entered");
       Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(user.getUserId(),user.getPassword())
        );
       if (authentication.isAuthenticated()){
           String jwtToken = jwtService.generateToken(user.getUserId());
           System.out.println("jwt token: "+jwtToken);
           User user1;
           try {
                user1 = authService.getUser(user.getUserId());
           } catch (Exception e) {
               return new ResponseEntity<String>("Something went wrong",HttpStatus.UNAUTHORIZED);
           }
           return ResponseEntity.status(HttpStatus.OK).body(Map.of("token",jwtToken,"user", Map.of("id",user1.getUserId(),"hash",user1.getHashKey()==null?"":user1.getHashKey())));
       }
       return new ResponseEntity<>("Login Failed",HttpStatus.UNAUTHORIZED);

    }

}
