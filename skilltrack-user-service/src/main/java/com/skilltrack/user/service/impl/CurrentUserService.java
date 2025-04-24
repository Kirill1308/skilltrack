package com.skilltrack.user.service.impl;

import com.skilltrack.jwt.model.JwtUserDetails;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class CurrentUserService {

    public JwtUserDetails getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.isAuthenticated() &&
            authentication.getPrincipal() instanceof JwtUserDetails) {
            return (JwtUserDetails) authentication.getPrincipal();
        }

        return null;
    }

    public UUID getCurrentUserId() {
        JwtUserDetails userDetails = getCurrentUser();
        return userDetails != null ? userDetails.getUserId() : null;
    }

    public String getCurrentUsername() {
        JwtUserDetails userDetails = getCurrentUser();
        return userDetails != null ? userDetails.getUsername() : null;
    }

    public boolean hasAuthority(String authority) {
        JwtUserDetails userDetails = getCurrentUser();
        return userDetails != null && userDetails.getAuthorities().contains(authority);
    }
}
