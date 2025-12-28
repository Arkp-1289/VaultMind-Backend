package com.arkp.VaultMind.service;

import com.arkp.VaultMind.dto.profile.PostProfileReqDto;
import com.arkp.VaultMind.dto.profile.ProfileResponseDto;
import com.arkp.VaultMind.dto.profile.UpdateProfileReqDto;
import com.arkp.VaultMind.model.User;
import com.arkp.VaultMind.model.UserProfile;
import com.arkp.VaultMind.repo.ProfileRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class ProfileService {

    @Autowired
    ProfileRepo profileRepo;

    public ResponseEntity<?> setProfile(User loggedUser, PostProfileReqDto postReqDto) {
        try {
            UserProfile profile = new UserProfile();
            profile.setName(postReqDto.getName());
            profile.setEmail(postReqDto.getEmail());
            profile.setPhone(postReqDto.getPhone());
            profile.setUser(loggedUser);
            UserProfile userProfile = profileRepo.save(profile);
            return new ResponseEntity<>(userProfile, HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>("Email already exists",HttpStatus.CONFLICT);
        }
    }

    public ResponseEntity<?> getProfile(User loggedUser) {
        try{
            System.out.println("user: "+loggedUser);
            ProfileResponseDto profile=   profileRepo.findByUser(loggedUser).orElseThrow();
         return new ResponseEntity<>(profile,HttpStatus.OK);
        } catch (Exception e){
            return  new ResponseEntity<>("no profile found",HttpStatus.NOT_FOUND);
        }
    }


    public ResponseEntity<?> updateProfile(User loggedUser, UpdateProfileReqDto updateDto) {

        UserProfile profile = profileRepo.findByProfileIdAndUser(updateDto.getProfileId(),loggedUser).orElseThrow(() -> new RuntimeException("No user found for"));
        profile.setName(updateDto.getName());
        profile.setEmail(updateDto.getEmail());
        profile.setPhone(updateDto.getPhone());
        try {
            UserProfile profile1 = profileRepo.save(profile);
            return new ResponseEntity<>(profile1,HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("update is not allowed due to duplicate email",HttpStatus.CONFLICT);
        }

    }

}
