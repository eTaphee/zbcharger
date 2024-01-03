package com.zerobase.zbcharger.domain.membership.dto;

import static com.zerobase.zbcharger.validator.constant.ValidationMessage.INVALID_CARD_TYPE;

import com.zerobase.zbcharger.domain.membership.entity.CardType;
import com.zerobase.zbcharger.validator.annotation.EnumValue;
import jakarta.persistence.Convert;

/**
 * 멤버십 카드 발급 요청
 *
 * @param cardType 카드 타입
 */
public record IssueMembershipCardRequest(
    @EnumValue(enumClass = CardType.class, message = INVALID_CARD_TYPE) String cardType
) {

}
