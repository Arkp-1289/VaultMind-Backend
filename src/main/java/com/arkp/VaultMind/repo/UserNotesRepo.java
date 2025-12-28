package com.arkp.VaultMind.repo;

import com.arkp.VaultMind.dto.notes.UserNotesDto;
import com.arkp.VaultMind.model.User;
import com.arkp.VaultMind.model.UserNotes;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserNotesRepo  extends JpaRepository<UserNotes,Integer> {
    List<UserNotesDto> findByuser(User loggedUser);

    @Modifying
    @Transactional
    int deleteByIdAndUser(int id, User loggedUser );


   Optional< UserNotes> findByIdAndUser(int noteId, User loggedUser);
}
