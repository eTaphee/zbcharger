package com.zerobase.zbcharger.domain.membership.service;

import static com.zerobase.zbcharger.domain.membership.entity.CardType.MOBILE;
import static com.zerobase.zbcharger.domain.membership.entity.CardType.PHYSICAL;
import static com.zerobase.zbcharger.exception.constant.ErrorCode.ACCESS_DENIED;
import static com.zerobase.zbcharger.exception.constant.ErrorCode.MEMBERSHIP_CARD_ALREADY_REGISTERED;
import static com.zerobase.zbcharger.exception.constant.ErrorCode.MEMBERSHIP_CARD_NOT_FOUND;
import static com.zerobase.zbcharger.exception.constant.ErrorCode.ONLY_PHYSICAL_CARD_COULD_REGISTER;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import com.zerobase.zbcharger.domain.member.entity.Member;
import com.zerobase.zbcharger.domain.membership.dao.MembershipCardRepository;
import com.zerobase.zbcharger.domain.membership.dto.RegisterMembershipCardRequest;
import com.zerobase.zbcharger.domain.membership.entity.MembershipCard;
import com.zerobase.zbcharger.exception.CustomException;
import java.time.LocalDateTime;
import java.util.Optional;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class RegisterMembershipServiceTest {

    @Mock
    private MembershipCardRepository membershipCardRepository;

    @InjectMocks
    private RegisterMembershipService registerMembershipService;

    private static final String MOCK_CARD_NUMBER = "1000-0000-0000-0001";

    private static final Member MOCK_MEMBER = mock(Member.class);

    private static final RegisterMembershipCardRequest MOCK_REQUEST = new RegisterMembershipCardRequest(
        MOCK_CARD_NUMBER);

    @BeforeAll
    static void setup() {
        given(MOCK_MEMBER.getId()).willReturn(1L);
    }

    @Test
    @DisplayName("멤버십 카드 등록 성공")
    void successRegisterCard() {
        // given
        MembershipCard membershipCard = mock(MembershipCard.class);
        given(membershipCard.getCardType()).willReturn(PHYSICAL);
        given(membershipCard.getMember()).willReturn(MOCK_MEMBER);

        given(membershipCardRepository.findById(anyString()))
            .willReturn(Optional.of(membershipCard));

        // when
        registerCard();

        // then
        verify(membershipCard).register();
    }

    @Test
    @DisplayName("멤버십 카드 등록 실패 - 카드 없음")
    void failRegisterCard_membership_card_not_found() {
        // given
        given(membershipCardRepository.findById(anyString()))
            .willReturn(Optional.empty());

        // when
        CustomException customException = assertThrows(CustomException.class, this::registerCard);

        // then
        assertEquals(MEMBERSHIP_CARD_NOT_FOUND, customException.getErrorCode());
    }

    @Test
    @DisplayName("멤버십 카드 등록 실패 - 물리 카드 아님")
    void failRegisterCard_only_physical_card() {
        // given
        MembershipCard membershipCard = mock(MembershipCard.class);
        given(membershipCard.getCardType()).willReturn(MOBILE);

        given(membershipCardRepository.findById(anyString()))
            .willReturn(Optional.of(membershipCard));

        // when
        CustomException customException = assertThrows(CustomException.class, this::registerCard);

        // then
        assertEquals(ONLY_PHYSICAL_CARD_COULD_REGISTER, customException.getErrorCode());
    }

    @Test
    @DisplayName("멤버십 카드 등록 실패 - 이미 등록된 카드")
    void failRegisterCard_already_registered() {
        // given
        MembershipCard membershipCard = mock(MembershipCard.class);
        given(membershipCard.getCardType()).willReturn(PHYSICAL);
        given(membershipCard.getRegisteredAt()).willReturn(LocalDateTime.now());

        given(membershipCardRepository.findById(anyString()))
            .willReturn(Optional.of(membershipCard));

        // when
        CustomException customException = assertThrows(CustomException.class, this::registerCard);

        // then
        assertEquals(MEMBERSHIP_CARD_ALREADY_REGISTERED, customException.getErrorCode());
    }

    @Test
    @DisplayName("멤버십 카드 등록 실패 - 카드 소유주 아님")
    void failRegisterCard_access_denied() {
        // given
        Member member = mock(Member.class);
        given(member.getId()).willReturn(2L);

        MembershipCard membershipCard = mock(MembershipCard.class);
        given(membershipCard.getCardType()).willReturn(PHYSICAL);
        given(membershipCard.getMember()).willReturn(member);

        given(membershipCardRepository.findById(anyString()))
            .willReturn(Optional.of(membershipCard));

        // when
        CustomException customException = assertThrows(CustomException.class, this::registerCard);

        // then
        assertEquals(ACCESS_DENIED, customException.getErrorCode());
    }

    void registerCard() {
        registerMembershipService.registerCard(MOCK_MEMBER, MOCK_REQUEST);
    }
}