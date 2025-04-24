package com.skilltrack.user.service.impl;

import com.skilltrack.common.dto.skill.request.UserSkillCreateRequest;
import com.skilltrack.common.dto.skill.request.UserSkillUpdateRequest;
import com.skilltrack.common.dto.skill.response.UserSkillResponse;
import com.skilltrack.user.service.UserSkillService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserSkillServiceImpl implements UserSkillService {

    @Override
    public UserSkillResponse addUserSkill(UserSkillCreateRequest request) {
        return null;
    }

    @Override
    public UserSkillResponse getUserSkillById(UUID id) {
        return null;
    }

    @Override
    public List<UserSkillResponse> getUserSkillsByUserId(UUID userId) {
        return List.of();
    }

    @Override
    public UserSkillResponse updateUserSkill(UUID id, UserSkillUpdateRequest request) {
        return null;
    }

    @Override
    public void deleteUserSkill(UUID id) {

    }

    @Override
    public UserSkillResponse verifyUserSkill(UUID id) {
        return null;
    }

    @Override
    public UserSkillResponse unverifyUserSkill(UUID id) {
        return null;
    }
}
