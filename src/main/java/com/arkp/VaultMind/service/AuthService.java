package com.arkp.VaultMind.service;


import com.arkp.VaultMind.dto.RegisterRequest;
import com.arkp.VaultMind.model.User;
import com.arkp.VaultMind.repo.AuthRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {

    @Autowired
    AuthRepo authRepo;

    @Autowired
    PasswordEncoder encoder;

    public User userRegister(RegisterRequest user) {
       User found= authRepo.findById(user.getUserId()).orElse(null);

        if (found!=null){
            return null;
        }
        User user1= new User();
        user1.setUserId(user.getUserId());
        user1.setPassword(encoder.encode(user.getPassword()));
        return authRepo.save(user1);

    }

    public ResponseEntity<String> userLogin(User user) {
       User user1= authRepo.findById(user.getUserId()).orElse(null);
       if (user1==null){
           return new ResponseEntity<>("User not found",HttpStatus.UNAUTHORIZED);
       }
       if (!encoder.matches(user.getPassword(),user1.getPassword())){
           return new ResponseEntity<>("Invalid credentials", HttpStatus.UNAUTHORIZED);
       }
       return new ResponseEntity<>("Login Successful", HttpStatus.OK);

    }

    public User getUser(String userId) {
        return authRepo.findById(userId).orElseThrow();
    }
}
