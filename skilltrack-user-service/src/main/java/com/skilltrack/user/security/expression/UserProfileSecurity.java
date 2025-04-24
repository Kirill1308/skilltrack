package com.skilltrack.user.security.expression;

import com.skilltrack.user.service.impl.CurrentUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component("profileSecurity")
@RequiredArgsConstructor
public class UserProfileSecurity {

    private final CurrentUserService currentUserService;

    public boolean isCurrentUser(UUID userId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            return false;
        }

        UUID currentUserId = currentUserService.getCurrentUserId();
        return currentUserId != null && currentUserId.equals(userId);
    }
}
