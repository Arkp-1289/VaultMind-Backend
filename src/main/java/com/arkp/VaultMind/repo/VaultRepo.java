package com.arkp.VaultMind.repo;

import com.arkp.VaultMind.dto.vault.VaultResDto;
import com.arkp.VaultMind.model.User;
import com.arkp.VaultMind.model.UserVault;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface VaultRepo extends JpaRepository<UserVault,Integer> {
   List<VaultResDto>  findByUser(User loggedUser);

    Optional<UserVault> findByIdAndUser(int id,User loggedUser);

   Optional<List<UserVault>> findAllByUser(User loggedUser);

    @Transactional
    @Modifying
    int deleteByUser(User loggedUser);

}
