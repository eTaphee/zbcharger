package com.zerobase.zbcharger.domain.membership.dto;

import com.zerobase.zbcharger.validator.annotation.MembershipCard;

/**
 * 멤버십 카드 등록 요청
 *
 * @param cardNo 카드 번호
 */
public record RegisterMembershipCardRequest(
    @MembershipCard String cardNo
) {

}
