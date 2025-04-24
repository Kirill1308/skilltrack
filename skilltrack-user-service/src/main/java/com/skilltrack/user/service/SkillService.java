package com.skilltrack.user.service;

import com.skilltrack.common.dto.skill.request.SkillCreateRequest;
import com.skilltrack.common.dto.skill.request.SkillUpdateRequest;
import com.skilltrack.common.dto.skill.response.SkillResponse;
import com.skilltrack.common.dto.skill.response.SkillStatisticsResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

public interface SkillService {

    SkillResponse createSkill(SkillCreateRequest request);

    SkillResponse getSkillById(UUID id);

    Page<SkillResponse> getAllSkills(String category, String query, Pageable pageable);

    SkillResponse updateSkill(UUID id, SkillUpdateRequest request);

    void deleteSkill(UUID id);

    SkillStatisticsResponse getSkillStatistics();

    List<SkillResponse> getTopSkills(int limit);
}
