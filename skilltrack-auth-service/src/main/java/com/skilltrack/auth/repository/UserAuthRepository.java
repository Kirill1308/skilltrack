package com.skilltrack.auth.repository;

import com.skilltrack.auth.model.UserAuth;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserAuthRepository extends JpaRepository<UserAuth, UUID> {
    Optional<UserAuth> findByEmail(String email);

    Optional<UserAuth> findByUserId(UUID userId);
}
