package com.arkp.VaultMind.service;

import com.arkp.VaultMind.dto.UserNotesRequest;
import com.arkp.VaultMind.model.User;
import com.arkp.VaultMind.model.UserNotes;
import com.arkp.VaultMind.repo.UserNotesRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;


@Service
public class UserNotesService {

    @Autowired
    UserNotesRepo userNotesRepo;

    public ResponseEntity<UserNotes>   insertNotes(User loggedUser, UserNotesRequest userNotesRequest) {
        System.out.println("user-service: "+loggedUser);
        UserNotes userNotes = new UserNotes();
        userNotes.setTitle(userNotesRequest.getTitle());
        userNotes.setContent(userNotesRequest.getContent());
        userNotes.setUser(loggedUser);
        userNotesRepo.save(userNotes);


        return new ResponseEntity<>(userNotes, HttpStatus.OK);
    }
}
