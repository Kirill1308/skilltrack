package com.skilltrack.user.service.impl;

import com.skilltrack.common.dto.skill.request.SkillCreateRequest;
import com.skilltrack.common.dto.skill.request.SkillUpdateRequest;
import com.skilltrack.common.dto.skill.response.SkillResponse;
import com.skilltrack.common.dto.skill.response.SkillStatisticsResponse;
import com.skilltrack.user.service.SkillService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SkillServiceImpl implements SkillService {

    @Override
    public SkillResponse createSkill(SkillCreateRequest request) {
        return null;
    }

    @Override
    public SkillResponse getSkillById(UUID id) {
        return null;
    }

    @Override
    public Page<SkillResponse> getAllSkills(String category, String query, Pageable pageable) {
        return null;
    }

    @Override
    public SkillResponse updateSkill(UUID id, SkillUpdateRequest request) {
        return null;
    }

    @Override
    public void deleteSkill(UUID id) {}

    @Override
    public SkillStatisticsResponse getSkillStatistics() {
        return null;
    }

    @Override
    public List<SkillResponse> getTopSkills(int limit) {
        return List.of();
    }
}
