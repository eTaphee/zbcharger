package com.zerobase.zbcharger.domain.membership.service;

import static com.zerobase.zbcharger.exception.constant.ErrorCode.ACCESS_DENIED;
import static com.zerobase.zbcharger.exception.constant.ErrorCode.MEMBERSHIP_CARD_ALREADY_REGISTERED;
import static com.zerobase.zbcharger.exception.constant.ErrorCode.MEMBERSHIP_CARD_NOT_FOUND;
import static com.zerobase.zbcharger.exception.constant.ErrorCode.ONLY_PHYSICAL_CARD_COULD_REGISTER;

import com.zerobase.zbcharger.domain.member.entity.Member;
import com.zerobase.zbcharger.domain.membership.dao.MembershipCardRepository;
import com.zerobase.zbcharger.domain.membership.dto.RegisterMembershipCardRequest;
import com.zerobase.zbcharger.domain.membership.entity.CardType;
import com.zerobase.zbcharger.domain.membership.entity.MembershipCard;
import com.zerobase.zbcharger.exception.CustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 멤버십 카드 등록 서비스
 */
@Service
@RequiredArgsConstructor
public class RegisterMembershipService {

    private final MembershipCardRepository membershipCardRepository;

    /**
     * 멤버십 카드 등록
     *
     * @param member  회원
     * @param request 멤버십 카드 등록 요청
     */
    @Transactional
    public void registerCard(Member member, RegisterMembershipCardRequest request) {
        MembershipCard membershipCard = membershipCardRepository.findById(request.cardNo())
            .orElseThrow(() -> new CustomException(MEMBERSHIP_CARD_NOT_FOUND));

        checkCardTypeIsPhysical(membershipCard);
        checkCardAlreadyRegistered(membershipCard);
        checkAuthority(membershipCard, member);

        membershipCard.register();
    }

    /**
     * 카드 타입이 물리카드인지 확인
     *
     * @param membershipCard 멤버십 카드
     */
    private void checkCardTypeIsPhysical(MembershipCard membershipCard) {
        if (membershipCard.getCardType() != CardType.PHYSICAL) {
            throw new CustomException(ONLY_PHYSICAL_CARD_COULD_REGISTER);
        }
    }

    /**
     * 카드가 이미 등록되었는지 확인
     *
     * @param membershipCard 멤버십 카드
     */
    private void checkCardAlreadyRegistered(MembershipCard membershipCard) {
        if (membershipCard.getRegisteredAt() != null) {
            throw new CustomException(MEMBERSHIP_CARD_ALREADY_REGISTERED);
        }
    }

    /**
     * 권한 확인
     *
     * @param membershipCard 멤버십 카드
     * @param member         회원
     */
    private void checkAuthority(MembershipCard membershipCard, Member member) {
        if (!membershipCard.getMember().getId().equals(member.getId())) {
            throw new CustomException(ACCESS_DENIED);
        }
    }
}
