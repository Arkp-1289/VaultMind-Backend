package com.arkp.VaultMind.controller;

import com.arkp.VaultMind.dto.UserNotesRequest;
import com.arkp.VaultMind.dto.UserNotesDto;
import com.arkp.VaultMind.model.User;
import com.arkp.VaultMind.model.UserNotes;
import com.arkp.VaultMind.service.JwtService;
import com.arkp.VaultMind.service.UserNotesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/user")
public class UserNotesController {

    @Autowired
    UserNotesService userNotesService;

    @Autowired
    JwtService jwtService;

    @PostMapping("/notes")
    public ResponseEntity<UserNotes> insertNotes(@AuthenticationPrincipal User loggeduser,
                                                 @RequestBody UserNotesRequest userNotesRequest){

       return  userNotesService.insertNotes(loggeduser,userNotesRequest);
    }

    @GetMapping("/notes")
    public ResponseEntity<List<UserNotesDto>> getNotes(@AuthenticationPrincipal User loggedUser){

        if (loggedUser==null){new ResponseEntity<>(Optional.ofNullable(null), HttpStatus.UNAUTHORIZED);}
        System.out.println("user-notes: userName: "+loggedUser.getUserId());
        return userNotesService.getNotes(loggedUser);
    }

    @DeleteMapping("/notes/{id}")
    public ResponseEntity<String> deleteNotes(@PathVariable int id,@AuthenticationPrincipal User loggedUser) {

        return userNotesService.deleteNotes(id, loggedUser);


    }

    @PutMapping("/notes")
    public ResponseEntity<String> updateNotes(@RequestBody UserNotesDto userNote
    ,@AuthenticationPrincipal User loggedUser){
        try {
           return userNotesService.updateNotes(userNote, loggedUser);
        } catch (Exception e){
            return new ResponseEntity<>("note not found or Not owned by user",HttpStatus.NOT_ACCEPTABLE);
        }
    }


}
