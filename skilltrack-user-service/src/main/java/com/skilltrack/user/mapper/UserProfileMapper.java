package com.skilltrack.user.mapper;

import com.skilltrack.common.dto.user.request.UserProfileCreateRequest;
import com.skilltrack.common.dto.user.response.UserProfileResponse;
import com.skilltrack.user.model.Department;
import com.skilltrack.user.model.UserProfile;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class UserProfileMapper {

    private final SkillMapper skillMapper;
    private final CertificationMapper certificationMapper;

    public UserProfileResponse toUserProfileResponse(UserProfile user) {
        return UserProfileResponse.builder()
                .id(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .department(user.getDepartment() != null ? user.getDepartment().getName() : null)
                .jobTitle(user.getJobTitle())
                .profilePictureUrl(user.getProfilePictureUrl())
                .skills(skillMapper.toSummaryResponseList(user.getUserSkills()))
                .certifications(certificationMapper.toSummaryResponseList(user.getCertifications()))
                .createdAt(user.getCreatedAt())
                .updatedAt(user.getUpdatedAt())
                .build();
    }

    public List<UserProfileResponse> toUserProfileResponseList(List<UserProfile> users) {
        return users.stream()
                .map(this::toUserProfileResponse)
                .toList();
    }

    public UserProfile toUserProfile(UserProfileCreateRequest request, Department department) {
        return UserProfile.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .jobTitle(request.getJobTitle())
                .bio(request.getBio())
                .department(department)
                .build();
    }
}
