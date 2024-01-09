package com.zerobase.zbcharger.domain.charger.dto.admin;

import com.zerobase.zbcharger.validator.annotation.Phone;
import jakarta.validation.constraints.NotBlank;

/**
 * 회사 수정 요청
 *
 * @param name     회사 이름
 * @param tel      연락처
 * @param operator 운영기관
 */
public record UpdateCompanyRequest(
    @NotBlank String name,
    @Phone String tel,
    @NotBlank String operator
) {

}
