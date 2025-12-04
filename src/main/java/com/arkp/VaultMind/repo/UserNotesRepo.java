package com.arkp.VaultMind.repo;

import com.arkp.VaultMind.model.UserNotes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserNotesRepo  extends JpaRepository<UserNotes,Integer> {
}
