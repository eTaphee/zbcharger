package com.zerobase.zbcharger.domain.membership.dto;

import com.zerobase.zbcharger.domain.membership.entity.CardType;
import com.zerobase.zbcharger.domain.membership.entity.MembershipCard;

/**
 * 멤버십 카드 발급 응답
 *
 * @param cardNo   카드 번호
 * @param cardType 카드 타입
 */
public record IssueMembershipCardResponse(
    String cardNo,
    CardType cardType
) {

    public static IssueMembershipCardResponse from(MembershipCard membershipCard) {
        return new IssueMembershipCardResponse(
            membershipCard.getCardNo(),
            membershipCard.getCardType()
        );
    }
}
