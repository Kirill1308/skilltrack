package com.skilltrack.user.service.impl;

import com.skilltrack.common.dto.user.request.UserProfileCreateRequest;
import com.skilltrack.common.dto.user.request.UserProfileUpdateRequest;
import com.skilltrack.common.dto.user.response.UserProfileResponse;
import com.skilltrack.user.exception.DepartmentNotFoundException;
import com.skilltrack.user.exception.UserProfileAlreadyExistsException;
import com.skilltrack.user.exception.UserProfileNotFoundException;
import com.skilltrack.user.mapper.UserProfileMapper;
import com.skilltrack.user.model.Department;
import com.skilltrack.user.model.UserProfile;
import com.skilltrack.user.repository.DepartmentRepository;
import com.skilltrack.user.repository.UserProfileRepository;
import com.skilltrack.user.service.UserProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserProfileServiceImpl implements UserProfileService {

    private final CurrentUserService currentUserService;
    private final UserProfileRepository profileRepository;
    private final DepartmentRepository departmentRepository;
    private final UserProfileMapper profileMapper;

    @Override
    @Transactional
    public UserProfileResponse createUserProfile(UserProfileCreateRequest request) {
        if (profileRepository.existsByEmail(request.getEmail())) {
            throw new UserProfileAlreadyExistsException("User already exists with email: " + request.getEmail());
        }

        Department department = departmentRepository.findByName(request.getDepartment())
                .orElseThrow(() -> new DepartmentNotFoundException(request.getDepartment()));

        UserProfile profile = profileMapper.toUserProfile(request, department);

        UserProfile savedProfile = this.profileRepository.save(profile);

        return profileMapper.toUserProfileResponse(savedProfile);
    }

    @Override
    @Transactional(readOnly = true)
    public UserProfileResponse getUserProfile(UUID userId) {
        UserProfile profile = profileRepository.findById(userId)
                .orElseThrow(() -> new UserProfileNotFoundException(userId));

        return profileMapper.toUserProfileResponse(profile);
    }

    @Override
    @Transactional(readOnly = true)
    public UserProfileResponse getCurrentUserProfile() {
        UUID userId = currentUserService.getCurrentUserId();
        UserProfile profile = profileRepository.findById(userId)
                .orElseThrow(() -> new UserProfileNotFoundException(userId));

        return profileMapper.toUserProfileResponse(profile);
    }

    @Override
    @Transactional
    public UserProfileResponse updateUserProfile(UUID id, UserProfileUpdateRequest request) {
        return null;
    }

    @Override
    @Transactional(readOnly = true)
    public List<UserProfileResponse> getUserProfilesByDepartment(UUID departmentId) {
        List<UserProfile> profiles = profileRepository.findByDepartmentId(departmentId);
        return profileMapper.toUserProfileResponseList(profiles);
    }

    @Override
    @Transactional(readOnly = true)
    public List<UserProfileResponse> getUserProfilesByTeam(UUID teamId) {
        List<UserProfile> profiles = profileRepository.findByTeamId(teamId);
        return profileMapper.toUserProfileResponseList(profiles);
    }

    @Override
    @Transactional(readOnly = true)
    public void deleteUserProfile(UUID id) {
        UserProfile profile = profileRepository.findById(id)
                .orElseThrow(() -> new UserProfileNotFoundException(id));

        profileRepository.delete(profile);
    }
}
