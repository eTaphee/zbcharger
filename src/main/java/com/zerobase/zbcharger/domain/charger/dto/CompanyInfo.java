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

    @Override
    public boolean equals(final Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof CompanyInfo other)) {
            return false;
        }
        if (!other.canEqual(this)) {
            return false;
        }
        return this.id.equals(other.id);
    }

    protected boolean canEqual(final Object other) {
        return other instanceof CompanyInfo;
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    public Company toEntity(boolean isNew) {
        return Company.builder()
            .id(id)
            .name(name)
            .tel(call)
            .operator(operator)
            .isNew(isNew)
            .build();
    }
}
