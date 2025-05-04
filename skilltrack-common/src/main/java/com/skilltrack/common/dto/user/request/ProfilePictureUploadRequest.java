package com.skilltrack.common.dto.user.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class ProfilePictureUploadRequest {

    @NotNull(message = "Profile picture is required")
    private MultipartFile file;

}
