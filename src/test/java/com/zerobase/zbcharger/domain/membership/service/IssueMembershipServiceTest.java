package com.zerobase.zbcharger.domain.membership.service;

import static com.zerobase.zbcharger.domain.membership.entity.CardType.PHYSICAL;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

import com.zerobase.zbcharger.domain.member.entity.Member;
import com.zerobase.zbcharger.domain.membership.dao.MembershipCardRepository;
import com.zerobase.zbcharger.domain.membership.dto.IssueMembershipCardRequest;
import com.zerobase.zbcharger.domain.membership.entity.MembershipCard;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class IssueMembershipServiceTest {

    @Mock
    private MembershipCardRepository membershipCardRepository;

    @InjectMocks
    private IssueMembershipService issueMembershipService;

    private static final IssueMembershipCardRequest MOCK_REQUEST = new IssueMembershipCardRequest(
        PHYSICAL.name());

    private static final Member MOCK_MEMBER = mock(Member.class);

    @BeforeAll
    static void setup() {
        given(MOCK_MEMBER.getId()).willReturn(1L);
    }

    @Test
    @DisplayName("멤버십 카드 발급 성공")
    void successIssueCard() {
        // given
        given(membershipCardRepository.existsByMemberAndCardType(any(Member.class), any()))
            .willReturn(false);

        // when
        MembershipCard membershipCard = issueCard();

        // then
    }

    MembershipCard issueCard() {
        return issueMembershipService.issueCard(MOCK_MEMBER, MOCK_REQUEST);
    }
}