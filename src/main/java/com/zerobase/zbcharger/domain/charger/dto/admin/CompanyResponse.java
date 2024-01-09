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
    boolean deletedYn,
    LocalDateTime createdAt,
    LocalDateTime updatedAt,
    LocalDateTime deletedAt
) {

    public static CompanyResponse fromEntity(Company company) {
        return CompanyResponse.builder()
            .id(company.getId())
            .name(company.getName())
            .tel(company.getTel())
            .deletedYn(company.isDeleted())
            .operator(company.getOperator())
            .createdAt(company.getCreatedAt())
            .updatedAt(company.getUpdatedAt())
            .deletedAt(company.getDeletedAt())
            .build();
    }
}
