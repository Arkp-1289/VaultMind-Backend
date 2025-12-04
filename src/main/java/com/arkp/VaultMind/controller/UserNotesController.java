package com.arkp.VaultMind.controller;

import com.arkp.VaultMind.dto.UserNotesRequest;
import com.arkp.VaultMind.model.User;
import com.arkp.VaultMind.model.UserNotes;
import com.arkp.VaultMind.service.UserNotesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserNotesController {

    @Autowired
    UserNotesService userNotesService;

    @PostMapping("/notes")
    public ResponseEntity<UserNotes> getNotes(@AuthenticationPrincipal User loggeduser,
                                              @RequestBody UserNotesRequest userNotesRequest){
        System.out.println("conrtoller-user; "+loggeduser);
       return  userNotesService.insertNotes(loggeduser,userNotesRequest);

    }


}
