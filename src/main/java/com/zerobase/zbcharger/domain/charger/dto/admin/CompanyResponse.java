package com.zerobase.zbcharger.domain.charger.dto.admin;

import com.zerobase.zbcharger.domain.charger.entity.Company;
import java.time.LocalDateTime;
import lombok.Builder;

@Builder
public record CompanyResponse(
    String id,
    String name,
    String tel,
    String operator,
    LocalDateTime createdAt,
    LocalDateTime updatedAt
) {

    public static CompanyResponse fromEntity(Company company) {
        return CompanyResponse.builder()
            .id(company.getId())
            .name(company.getName())
            .tel(company.getTel())
            .operator(company.getOperator())
            .createdAt(company.getCreatedAt())
            .updatedAt(company.getUpdatedAt())
            .build();
    }
}
