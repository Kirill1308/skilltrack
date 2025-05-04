package com.skilltrack.user.service.impl;

import com.skilltrack.common.dto.skill.request.CertificationCreateRequest;
import com.skilltrack.common.dto.skill.response.CertificationResponse;
import com.skilltrack.user.service.CertificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CertificationServiceImpl implements CertificationService {

    @Override
    public CertificationResponse createCertification(CertificationCreateRequest request) {
        return null;
    }

    @Override
    public CertificationResponse getCertificationById(UUID id) {
        return null;
    }

    @Override
    public Page<CertificationResponse> getAllCertifications(Pageable pageable) {
        return null;
    }

    @Override
    public List<CertificationResponse> getUserCertifications(UUID userId) {
        return List.of();
    }

    @Override
    public void deleteCertification(UUID id) {
    }

}
