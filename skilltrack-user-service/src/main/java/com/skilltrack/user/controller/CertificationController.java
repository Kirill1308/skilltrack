package com.skilltrack.user.controller;

import com.skilltrack.common.dto.skill.request.CertificationCreateRequest;
import com.skilltrack.common.dto.skill.response.CertificationResponse;
import com.skilltrack.user.service.CertificationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/certifications")
@RequiredArgsConstructor
public class CertificationController {

    private final CertificationService certificationService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public CertificationResponse createCertification(
            @Valid @RequestBody CertificationCreateRequest request) {
        return certificationService.createCertification(request);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN', 'MANAGER')")
    public CertificationResponse getCertificationById(@PathVariable UUID id) {
        return certificationService.getCertificationById(id);
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('USER', 'ADMIN', 'MANAGER')")
    public Page<CertificationResponse> getAllCertifications(Pageable pageable) {
        return certificationService.getAllCertifications(pageable);
    }

    @GetMapping("/user/{userId}")
    @PreAuthorize("@userSecurity.isCurrentUser(#userId) or hasRole('ADMIN') or hasRole('MANAGER')")
    public List<CertificationResponse> getUserCertifications(@PathVariable UUID userId) {
        return certificationService.getUserCertifications(userId);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("@certificationSecurity.isOwner(#id) or hasRole('ADMIN')")
    public void deleteCertification(@PathVariable UUID id) {
        certificationService.deleteCertification(id);
    }
}
