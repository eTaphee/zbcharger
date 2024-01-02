package com.zerobase.zbcharger.domain.charger.dto;

import com.zerobase.zbcharger.domain.charger.entity.Company;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * 회사 정보
 */
@Builder
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class CompanyInfo {

    /**
     * 회사 아이디
     */
    @Getter
    private final String id;

    /**
     * 회사 이름
     */
    private final String name;

    /**
     * 운영기관 연락처
     */
    private final String call;

    /**
     * 운영기관
     */
    private final String operator;

    public Company toEntity() {
        return Company.builder()
            .id(id)
            .name(name)
            .tel(call)
            .operator(operator)
            .build();
    }
}
