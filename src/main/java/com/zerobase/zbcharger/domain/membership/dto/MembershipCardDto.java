package com.zerobase.zbcharger.domain.membership.dto;

import com.zerobase.zbcharger.domain.membership.entity.CardType;
import com.zerobase.zbcharger.domain.membership.entity.MembershipCard;

public record MembershipCardDto(
    String cardNo,
    CardType cardType
) {

    public static MembershipCardDto from(MembershipCard membershipCard) {
        return new MembershipCardDto(
            membershipCard.getCardNo(),
            membershipCard.getCardType()
        );
    }
}