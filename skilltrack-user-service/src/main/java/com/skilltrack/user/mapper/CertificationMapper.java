package com.skilltrack.user.mapper;

import com.skilltrack.common.dto.user.response.CertificationSummaryResponse;
import com.skilltrack.user.model.Certification;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;

@Component
public class CertificationMapper {
    public List<CertificationSummaryResponse> toSummaryResponseList(Set<Certification> certifications) {
        if (certifications == null || certifications.isEmpty()) {
            return List.of();
        }

        return certifications.stream()
                .map(certification -> CertificationSummaryResponse.builder()
                        .id(certification.getId())
                        .name(certification.getName())
                        .issuingOrganization(certification.getIssuingOrganization())
                        .issueDate(certification.getIssuedAt())
                        .expiryDate(certification.getExpiresAt())
                        .build())
                .toList();
    }
}
