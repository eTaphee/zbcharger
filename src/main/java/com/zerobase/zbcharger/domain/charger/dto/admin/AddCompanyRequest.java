package com.zerobase.zbcharger.domain.charger.dto.admin;

import com.zerobase.zbcharger.domain.charger.entity.Company;
import com.zerobase.zbcharger.validator.annotation.Phone;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

/**
 * 회사 추가 요청
 *
 * @param id       회사 아이디
 * @param name     회사 이름
 * @param tel      연락처
 * @param operator 운영기관
 */
public record AddCompanyRequest(
    @Size(min = 2, max = 2) String id,
    @NotNull String name,
    @Phone String tel,
    @NotBlank String operator
) {

    public Company toEntity() {
        return Company.builder()
            .id(id)
            .name(name)
            .tel(tel)
            .operator(operator)
            .build();
    }
}
