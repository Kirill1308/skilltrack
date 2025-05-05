package com.skilltrack.user.service.impl;

import com.skilltrack.common.dto.user.request.ProfilePictureUploadRequest;
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
import com.skilltrack.user.service.FileStorageService;
import com.skilltrack.user.service.UserProfileService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserProfileServiceImpl implements UserProfileService {

    private final CurrentUserService currentUserService;
    private final FileStorageService fileStorageService;
    private final UserProfileRepository profileRepository;
    private final DepartmentRepository departmentRepository;
    private final UserProfileMapper profileMapper;

    @Override
    @Transactional
    public UserProfileResponse createUserProfile(UserProfileCreateRequest request) {
        log.info("Creating user profile for email: {}", request.getEmail());

        if (profileRepository.existsByEmail(request.getEmail())) {
            log.warn("User profile creation failed - email already exists: {}", request.getEmail());
            throw new UserProfileAlreadyExistsException("User already exists with email: " + request.getEmail());
        }

        Department department = departmentRepository.findByName(request.getDepartment())
                .orElseThrow(() -> {
                    log.warn("Department not found: {}", request.getDepartment());
                    return new DepartmentNotFoundException(request.getDepartment());
                });

        log.debug("Found department: {} for user profile", department.getName());

        UserProfile profile = profileMapper.toUserProfile(request, department);
        UserProfile savedProfile = profileRepository.save(profile);

        log.info("User profile created successfully with ID: {}", savedProfile.getId());
        return profileMapper.toUserProfileResponse(savedProfile);
    }

    @Override
    @Transactional(readOnly = true)
    public UserProfileResponse getUserProfile(UUID userId) {
        log.info("Retrieving user profile for ID: {}", userId);

        UserProfile profile = profileRepository.findById(userId)
                .orElseThrow(() -> {
                    log.warn("User profile not found for ID: {}", userId);
                    return new UserProfileNotFoundException(userId);
                });

        log.debug("User profile retrieved successfully for ID: {}", userId);
        return profileMapper.toUserProfileResponse(profile);
    }

    @Override
    @Transactional(readOnly = true)
    public UserProfileResponse getCurrentUserProfile() {
        log.info("Retrieving current user profile");

        UUID userId = currentUserService.getCurrentUserId();
        log.debug("Current user ID: {}", userId);

        UserProfile profile = profileRepository.findById(userId)
                .orElseThrow(() -> {
                    log.warn("Current user profile not found for ID: {}", userId);
                    return new UserProfileNotFoundException(userId);
                });

        log.info("Current user profile retrieved successfully");
        return profileMapper.toUserProfileResponse(profile);
    }

    @Override
    @Transactional
    public UserProfileResponse updateUserProfile(UUID id, UserProfileUpdateRequest request) {
        log.info("Updating user profile for ID: {}", id);

        UserProfile profile = profileRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("User profile update failed - profile not found for ID: {}", id);
                    return new UserProfileNotFoundException(id);
                });

        Department department = departmentRepository.findByName(request.getDepartment())
                .orElseThrow(() -> {
                    log.warn("Department not found for update: {}", request.getDepartment());
                    return new DepartmentNotFoundException(request.getDepartment());
                });

        profileMapper.updateUserProfileFromRequest(profile, request, department);
        UserProfile updatedProfile = profileRepository.save(profile);

        log.info("User profile updated successfully for ID: {}", id);
        return profileMapper.toUserProfileResponse(updatedProfile);
    }

    @Override
    @Transactional
    public UserProfileResponse updateUserProfilePicture(UUID id, ProfilePictureUploadRequest request) {
        log.info("Updating profile picture for user ID: {}", id);

        UserProfile profile = profileRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Profile picture update failed - user not found for ID: {}", id);
                    return new UserProfileNotFoundException(id);
                });

        if (profile.getProfilePictureFilename() != null) {
            log.debug("Deleting existing profile picture: {}", profile.getProfilePictureFilename());
            fileStorageService.delete(profile.getProfilePictureFilename());
        }

        String fileName = fileStorageService.upload(request.getFile());
        log.debug("New profile picture uploaded with filename: {}", fileName);

        profile.setProfilePictureFilename(fileName);

        UserProfile updatedProfile = profileRepository.save(profile);

        log.info("Profile picture updated successfully for user ID: {}", id);
        return profileMapper.toUserProfileResponse(updatedProfile);
    }

    @Override
    @Transactional(readOnly = true)
    public List<UserProfileResponse> getUserProfilesByDepartment(UUID departmentId) {
        log.info("Retrieving user profiles for department ID: {}", departmentId);

        List<UserProfile> profiles = profileRepository.findByDepartmentId(departmentId);
        List<UserProfileResponse> responses = profileMapper.toUserProfileResponseList(profiles);

        log.info("Retrieved {} user profiles for department ID: {}", responses.size(), departmentId);
        return responses;
    }

    @Override
    @Transactional(readOnly = true)
    public List<UserProfileResponse> getUserProfilesByTeam(UUID teamId) {
        log.info("Retrieving user profiles for team ID: {}", teamId);

        List<UserProfile> profiles = profileRepository.findByTeamId(teamId);
        List<UserProfileResponse> responses = profileMapper.toUserProfileResponseList(profiles);

        log.info("Retrieved {} user profiles for team ID: {}", responses.size(), teamId);
        return responses;
    }

    @Override
    @Transactional(readOnly = true)
    public void deleteUserProfile(UUID id) {
        log.info("Deleting user profile for ID: {}", id);

        UserProfile profile = profileRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Profile deletion failed - user not found for ID: {}", id);
                    return new UserProfileNotFoundException(id);
                });

        profileRepository.delete(profile);

        log.info("User profile deleted successfully for ID: {}", id);
    }
}
