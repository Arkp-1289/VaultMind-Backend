package com.arkp.VaultMind.service;

import com.arkp.VaultMind.dto.UserNotesRequest;
import com.arkp.VaultMind.dto.UserNotesDto;
import com.arkp.VaultMind.model.User;
import com.arkp.VaultMind.model.UserNotes;
import com.arkp.VaultMind.repo.UserNotesRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;


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

    public ResponseEntity<List<UserNotesDto>> getNotes(User loggedUser) {
        List<UserNotesDto> notesList= userNotesRepo.findByuser(loggedUser);
        return new ResponseEntity<>(notesList,HttpStatus.OK);
    }

    public ResponseEntity<String> deleteNotes(int id, User loggedUser) {
        int rows_effected=  userNotesRepo.deleteByIdAndUser(id,loggedUser);
        if (rows_effected==0){return new ResponseEntity<>("Note not found or Not owned by user ",HttpStatus.UNAUTHORIZED);}
        return new ResponseEntity<>("Deleted Successfully",HttpStatus.OK);
    }

    public ResponseEntity<String> updateNotes(UserNotesDto updateNote ,User loggedUser) {
        int note_id= updateNote.getId();
        UserNotes userNotes= userNotesRepo.findByIdAndUser(note_id,loggedUser).orElseThrow();
        userNotes.setTitle(updateNote.getTitle());
        userNotes.setContent(updateNote.getContent());
        userNotesRepo.save(userNotes);
        return new ResponseEntity<>("Changes Saved",HttpStatus.OK);

    }
}
