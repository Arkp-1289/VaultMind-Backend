package com.arkp.VaultMind.service;

import com.arkp.VaultMind.model.User;
import com.arkp.VaultMind.repo.AuthRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@Service
public class CustomUserDetailService  implements UserDetailsService {

    @Autowired
    AuthRepo authRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user=authRepo.findById(username).orElseThrow(()->new UsernameNotFoundException("user not found"));
        return new org.springframework.security.core.userdetails.User(
            user.getUserId(),
                user.getPassword(),
                new ArrayList<>()
        );

    }
}
