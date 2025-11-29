package com.arkp.VaultMind.controller;


import com.arkp.VaultMind.dto.RegisterRequest;
import com.arkp.VaultMind.model.User;
import com.arkp.VaultMind.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user")
public class AuthController {

    @Autowired
    AuthService authService;



    @PostMapping("register")
    public ResponseEntity<String> userRegister(@RequestBody RegisterRequest registerRequest){
        if (!registerRequest.getPassword().equals(registerRequest.getConfirmPassword())){
            return new ResponseEntity<>("Both Passwords should match", HttpStatus.NOT_ACCEPTABLE);
        }

        User user1= authService.userRegister(registerRequest);

        if (user1==null){
          return  new ResponseEntity<>("UserId  already exists",HttpStatus.NOT_ACCEPTABLE);
        }
        return new ResponseEntity<>("Account created Successfully",HttpStatus.OK);
    }

    @PostMapping("login")
    public ResponseEntity<String> userLogin(@RequestBody User user){
      return authService.userLogin(user);

    }

}
