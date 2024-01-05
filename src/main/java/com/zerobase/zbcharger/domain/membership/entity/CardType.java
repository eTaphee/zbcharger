package com.zerobase.zbcharger.domain.membership.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * 멤버십 카드 종류
 */
@Getter
@RequiredArgsConstructor
public enum CardType {

    /**
     * 실물 카드
     */
    PHYSICAL(1),

    /**
     * 모바일 카드
     */
    MOBILE(2);

    private final int value;
}
