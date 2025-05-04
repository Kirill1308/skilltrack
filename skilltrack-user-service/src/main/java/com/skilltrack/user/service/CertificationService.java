package com.skilltrack.user.service;

import com.skilltrack.common.dto.skill.request.CertificationCreateRequest;
import com.skilltrack.common.dto.skill.response.CertificationResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

public interface CertificationService {

    CertificationResponse createCertification(CertificationCreateRequest request);

    CertificationResponse getCertificationById(UUID id);

    Page<CertificationResponse> getAllCertifications(Pageable pageable);

    List<CertificationResponse> getUserCertifications(UUID userId);

    void deleteCertification(UUID id);

}
