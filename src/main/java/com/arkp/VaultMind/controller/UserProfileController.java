package com.arkp.VaultMind.controller;

import com.arkp.VaultMind.dto.profile.PostProfileReqDto;
import com.arkp.VaultMind.dto.profile.UpdateProfileReqDto;
import com.arkp.VaultMind.model.User;
import com.arkp.VaultMind.service.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("user")
public class UserProfileController {

    @Autowired
    ProfileService profileService;

    @PostMapping("/profile")
    public ResponseEntity<?> createProfile(@AuthenticationPrincipal User loggedUser , @RequestBody PostProfileReqDto postReqDto){
        if (loggedUser==null){return new ResponseEntity<>("Unable to find User", HttpStatus.UNAUTHORIZED);}
        return profileService.setProfile(loggedUser,postReqDto);
    }

    @GetMapping("/profile")
    public ResponseEntity<?> getProfil(@AuthenticationPrincipal User loggedUser){
        if (loggedUser==null){return new ResponseEntity<>("Unable to find User", HttpStatus.UNAUTHORIZED);}
        return profileService.getProfile(loggedUser);

    }

    @PutMapping("/profile")
    public ResponseEntity<?> updateProfile(@AuthenticationPrincipal User loggedUser, @RequestBody UpdateProfileReqDto updateDto){
        System.out.println("user: "+loggedUser);
        System.out.println("updateProfileDto: "+updateDto);
        if (loggedUser==null){return new ResponseEntity<>("Unable to find User", HttpStatus.UNAUTHORIZED);}
        return profileService.updateProfile(loggedUser,updateDto);
    }





}
