package com.arkp.VaultMind.repo;

import com.arkp.VaultMind.dto.UserNotesRequest;
import com.arkp.VaultMind.model.User;
import com.arkp.VaultMind.model.UserNotes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserNotesRepo  extends JpaRepository<UserNotes,Integer> {
    List<UserNotesRequest> findByuser(User loggedUser);
}
