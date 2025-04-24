package com.skilltrack.user.controller;

import com.skilltrack.common.dto.skill.request.SkillCreateRequest;
import com.skilltrack.common.dto.skill.request.SkillUpdateRequest;
import com.skilltrack.common.dto.skill.response.SkillResponse;
import com.skilltrack.common.dto.skill.response.SkillStatisticsResponse;
import com.skilltrack.user.service.SkillService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/skills")
@RequiredArgsConstructor
public class SkillController {

    private final SkillService skillService;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN') or hasRole('MANAGER')")
    public SkillResponse createSkill(@Valid @RequestBody SkillCreateRequest request) {
        return skillService.createSkill(request);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN', 'MANAGER')")
    public SkillResponse getSkillById(@PathVariable UUID id) {
        return skillService.getSkillById(id);
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('USER', 'ADMIN', 'MANAGER')")
    public Page<SkillResponse> getAllSkills(
            @RequestParam(required = false) String category,
            @RequestParam(required = false) String query,
            Pageable pageable) {
        return skillService.getAllSkills(category, query, pageable);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('MANAGER')")
    public SkillResponse updateSkill(
            @PathVariable UUID id,
            @Valid @RequestBody SkillUpdateRequest request) {
        return skillService.updateSkill(id, request);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public void deleteSkill(@PathVariable UUID id) {
        skillService.deleteSkill(id);
    }

    @GetMapping("/statistics")
    @PreAuthorize("hasRole('ADMIN') or hasRole('MANAGER')")
    public SkillStatisticsResponse getSkillStatistics() {
        return skillService.getSkillStatistics();
    }

    @GetMapping("/top-skills")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN', 'MANAGER')")
    public List<SkillResponse> getTopSkills(
            @RequestParam(defaultValue = "10") int limit) {
        return skillService.getTopSkills(limit);
    }
}
