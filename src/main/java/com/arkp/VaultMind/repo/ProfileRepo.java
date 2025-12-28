package com.arkp.VaultMind.repo;

import com.arkp.VaultMind.dto.profile.ProfileResponseDto;
import com.arkp.VaultMind.model.User;
import com.arkp.VaultMind.model.UserProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProfileRepo extends JpaRepository<UserProfile,Long> {
    Optional<ProfileResponseDto> findByUser(User loggedUser);

    Optional<UserProfile> findByProfileIdAndUser( Long profileId,User loggedUser);
}
