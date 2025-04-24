package com.skilltrack.user.controller;

import com.skilltrack.common.dto.user.request.UserProfileCreateRequest;
import com.skilltrack.common.dto.user.request.UserProfileUpdateRequest;
import com.skilltrack.common.dto.user.response.UserProfileResponse;
import com.skilltrack.user.service.UserProfileService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/user-profiles")
@RequiredArgsConstructor
public class UserProfileController {

    private final UserProfileService userProfileService;

    @PostMapping
    public UserProfileResponse createUserProfile(@Valid @RequestBody UserProfileCreateRequest request) {
        return userProfileService.createUserProfile(request);
    }

    @GetMapping("/{id}")
    public UserProfileResponse getUserProfile(@PathVariable UUID id) {
        return userProfileService.getUserProfile(id);
    }

    @GetMapping("/me")
    public UserProfileResponse getCurrentUserProfile() {
        return userProfileService.getCurrentUserProfile();
    }

    @PutMapping("/{id}")
    @PreAuthorize("@profileSecurity.isCurrentUser(#id) or hasRole('ADMIN')")
    public UserProfileResponse updateUserProfile(
            @PathVariable UUID id,
            @Valid @RequestBody UserProfileUpdateRequest request
    ) {
        return userProfileService.updateUserProfile(id, request);
    }

    @GetMapping("/by-department/{departmentId}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('MANAGER')")
    public List<UserProfileResponse> getUserProfilesByDepartment(@PathVariable UUID departmentId) {
        return userProfileService.getUserProfilesByDepartment(departmentId);
    }

    @GetMapping("/by-team/{teamId}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('MANAGER')")
    public List<UserProfileResponse> getUserProfilesByTeam(@PathVariable UUID teamId) {
        return userProfileService.getUserProfilesByTeam(teamId);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public void deleteUserProfile(@PathVariable UUID id) {
        userProfileService.deleteUserProfile(id);
    }

}
