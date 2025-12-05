package com.arkp.VaultMind.controller;

import com.arkp.VaultMind.dto.UserNotesRequest;
import com.arkp.VaultMind.model.User;
import com.arkp.VaultMind.model.UserNotes;
import com.arkp.VaultMind.service.JwtService;
import com.arkp.VaultMind.service.UserNotesService;
import jakarta.servlet.http.HttpServletRequest;
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
        System.out.println("conrtoller-user; "+loggeduser);
       return  userNotesService.insertNotes(loggeduser,userNotesRequest);

    }

    @GetMapping("/notes")
    public ResponseEntity<List<UserNotesRequest>> getNotes(@AuthenticationPrincipal User loggedUser){

        if (loggedUser==null){new ResponseEntity<>(Optional.ofNullable(null), HttpStatus.UNAUTHORIZED);}
        System.out.println("user-notes: userName: "+loggedUser.getUserId());
        return userNotesService.getNotes(loggedUser);
    }


}
