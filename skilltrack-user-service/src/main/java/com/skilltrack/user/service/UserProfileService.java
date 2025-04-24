package com.skilltrack.user.service;

import com.skilltrack.common.dto.user.request.UserProfileCreateRequest;
import com.skilltrack.common.dto.user.request.UserProfileUpdateRequest;
import com.skilltrack.common.dto.user.response.UserProfileResponse;

import java.util.List;
import java.util.UUID;

public interface UserProfileService {

    UserProfileResponse createUserProfile(UserProfileCreateRequest request);

    UserProfileResponse getUserProfile(UUID id);

    UserProfileResponse getCurrentUserProfile();

    UserProfileResponse updateUserProfile(UUID id, UserProfileUpdateRequest request);

    List<UserProfileResponse> getUserProfilesByDepartment(UUID departmentId);

    List<UserProfileResponse> getUserProfilesByTeam(UUID teamId);

    void deleteUserProfile(UUID id);

}
