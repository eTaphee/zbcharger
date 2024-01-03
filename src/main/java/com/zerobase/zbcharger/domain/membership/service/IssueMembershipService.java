package com.zerobase.zbcharger.domain.membership.service;

import static com.zerobase.zbcharger.exception.constant.ErrorCode.MEMBERSHIP_CARD_ALREADY_ISSUED;

import com.zerobase.zbcharger.aop.lock.DistributedLock;
import com.zerobase.zbcharger.domain.member.entity.Member;
import com.zerobase.zbcharger.domain.membership.dao.MembershipCardRepository;
import com.zerobase.zbcharger.domain.membership.dto.IssueMembershipCardRequest;
import com.zerobase.zbcharger.domain.membership.entity.CardType;
import com.zerobase.zbcharger.domain.membership.entity.MembershipCard;
import com.zerobase.zbcharger.exception.CustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 멤버십 카드 발급 서비스
 */
@Service
@RequiredArgsConstructor
public class IssueMembershipService {

    private static final int CARD_NO_LENGTH = 16;

    private final MembershipCardRepository membershipCardRepository;

    /**
     * 멤버십 카드 발급
     *
     * @param member  회원
     * @param request 멤버십 카드 발급 요청
     * @return 멤버십 카드
     */
    @Transactional
    @DistributedLock(key = "'issueMembershipCard'")
    public MembershipCard issueCard(Member member, IssueMembershipCardRequest request) {
        CardType cardType = CardType.valueOf(request.cardType());

        checkMembershipCardAlreadyIssued(member, cardType);

        MembershipCard membershipCard = MembershipCard.builder()
            .member(member)
            .cardType(cardType)
            .cardNo(generateMembershipCardNo(cardType))
            .build();

        return membershipCardRepository.save(membershipCard);
    }

    /**
     * 같은 타입의 멤버십 카드를 발급했는지 확인
     *
     * @param member   회원
     * @param cardType 카드 타입
     */
    private void checkMembershipCardAlreadyIssued(Member member, CardType cardType) {
        if (membershipCardRepository.existsByMemberAndCardType(member, cardType)) {
            throw new CustomException(MEMBERSHIP_CARD_ALREADY_ISSUED);
        }
    }

    /**
     * 순차적으로 카드 번호 생성
     *
     * @param cardType 카드 타입
     * @return 카드 번호
     */
    private String generateMembershipCardNo(CardType cardType) {
        long nextCardNo = membershipCardRepository.countByCardType(cardType) + 1;
        String tmp = String.format("%0" + (CARD_NO_LENGTH - 1) + "d", nextCardNo);
        StringBuilder cardNo = new StringBuilder();
        cardNo.append(cardType.getValue());
        for (int i = 0; i < tmp.length(); i++) {
            if (cardNo.length() % 5 == 4) {
                cardNo.append("-");
            }
            cardNo.append(tmp.charAt(i));
        }
        return cardNo.toString();
    }
}
