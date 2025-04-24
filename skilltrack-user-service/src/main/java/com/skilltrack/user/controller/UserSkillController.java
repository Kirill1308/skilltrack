package com.skilltrack.user.controller;

import com.skilltrack.common.dto.skill.request.UserSkillCreateRequest;
import com.skilltrack.common.dto.skill.request.UserSkillUpdateRequest;
import com.skilltrack.common.dto.skill.response.UserSkillResponse;
import com.skilltrack.user.service.UserSkillService;
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
@RequestMapping("/api/user-skills")
@RequiredArgsConstructor
public class UserSkillController {

    private final UserSkillService userSkillService;

    @PostMapping
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public UserSkillResponse addUserSkill(
            @Valid @RequestBody UserSkillCreateRequest request) {
        return userSkillService.addUserSkill(request);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN', 'MANAGER')")
    public UserSkillResponse getUserSkillById(@PathVariable UUID id) {
        return userSkillService.getUserSkillById(id);
    }

    @GetMapping("/user/{userId}")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN', 'MANAGER')")
    public List<UserSkillResponse> getUserSkillsByUserId(@PathVariable UUID userId) {
        return userSkillService.getUserSkillsByUserId(userId);
    }

    @PutMapping("/{id}")
    @PreAuthorize("(hasRole('USER') and @userSkillSecurity.isOwner(#id)) or hasRole('ADMIN')")
    public UserSkillResponse updateUserSkill(
            @PathVariable UUID id,
            @Valid @RequestBody UserSkillUpdateRequest request) {
        return userSkillService.updateUserSkill(id, request);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("(hasRole('USER') and @userSkillSecurity.isOwner(#id)) or hasRole('ADMIN')")
    public void deleteUserSkill(@PathVariable UUID id) {
        userSkillService.deleteUserSkill(id);
    }

    @PostMapping("/{id}/verify")
    @PreAuthorize("hasRole('ADMIN') or hasRole('MANAGER')")
    public UserSkillResponse verifyUserSkill(@PathVariable UUID id) {
        return userSkillService.verifyUserSkill(id);
    }

    @PostMapping("/{id}/unverify")
    @PreAuthorize("hasRole('ADMIN') or hasRole('MANAGER')")
    public UserSkillResponse unverifyUserSkill(@PathVariable UUID id) {
        return userSkillService.unverifyUserSkill(id);
    }
}
