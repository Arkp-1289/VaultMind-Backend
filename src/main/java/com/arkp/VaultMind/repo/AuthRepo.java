package com.arkp.VaultMind.repo;

import com.arkp.VaultMind.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthRepo extends JpaRepository<User,String> {

}
