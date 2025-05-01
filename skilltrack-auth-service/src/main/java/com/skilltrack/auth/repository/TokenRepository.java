package com.skilltrack.auth.repository;

import com.skilltrack.auth.model.Token;
import com.skilltrack.common.constant.TokenType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface TokenRepository extends JpaRepository<Token, UUID> {

    Optional<Token> findByTokenAndTokenType(String token, TokenType tokenType);

    @Modifying
    void deleteByUserIdAndTokenType(UUID userId, TokenType tokenType);

    @Modifying
    @Query("DELETE FROM Token t WHERE t.expiryDate < :now")
    void deleteAllExpiredTokens(@Param("now") LocalDateTime now);

    @Modifying
    void deleteByTokenAndTokenType(String tokenValue, TokenType tokenType);
}
