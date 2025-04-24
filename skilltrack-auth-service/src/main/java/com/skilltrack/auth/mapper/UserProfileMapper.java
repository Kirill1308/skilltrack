package com.skilltrack.auth.mapper;

import com.skilltrack.auth.dto.request.RegistrationRequest;
import com.skilltrack.common.dto.user.request.UserProfileCreateRequest;
import org.springframework.stereotype.Component;

@Component
public class UserProfileMapper {

    public UserProfileCreateRequest toCreateRequest(RegistrationRequest request) {
        return UserProfileCreateRequest.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .department(request.getDepartment())
                .jobTitle(request.getJobTitle())
                .bio(request.getBio())
                .build();
    }
}
