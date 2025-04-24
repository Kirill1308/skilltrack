package com.skilltrack.user.service;

import com.skilltrack.common.dto.skill.request.UserSkillCreateRequest;
import com.skilltrack.common.dto.skill.request.UserSkillUpdateRequest;
import com.skilltrack.common.dto.skill.response.UserSkillResponse;

import java.util.List;
import java.util.UUID;

public interface UserSkillService {

    UserSkillResponse addUserSkill(UserSkillCreateRequest request);

    UserSkillResponse getUserSkillById(UUID id);

    List<UserSkillResponse> getUserSkillsByUserId(UUID userId);

    UserSkillResponse updateUserSkill(UUID id, UserSkillUpdateRequest request);

    void deleteUserSkill(UUID id);

    UserSkillResponse verifyUserSkill(UUID id);

    UserSkillResponse unverifyUserSkill(UUID id);
}
