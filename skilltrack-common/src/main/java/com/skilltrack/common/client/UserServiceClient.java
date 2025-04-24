package com.skilltrack.common.client;

import com.skilltrack.common.dto.user.request.UserProfileCreateRequest;
import com.skilltrack.common.dto.user.response.UserProfileResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.UUID;

@FeignClient(name = "skilltrack-user-service", path = "/api/user-profiles")
public interface UserServiceClient {

    @GetMapping("/{id}")
    UserProfileResponse getUserProfileById(@PathVariable UUID id);

    @PostMapping
    UserProfileResponse createUserProfile(@RequestBody UserProfileCreateRequest request);
}
