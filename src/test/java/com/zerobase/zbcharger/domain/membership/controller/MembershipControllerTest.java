package com.zerobase.zbcharger.domain.membership.controller;

import static com.zerobase.zbcharger.domain.membership.entity.CardType.PHYSICAL;
import static com.zerobase.zbcharger.validator.constant.ValidationMessage.INVALID_CARD_TYPE;
import static com.zerobase.zbcharger.validator.constant.ValidationMessage.INVALID_MEMBERSHIP_CARD_FORMAT;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.zerobase.zbcharger.configuration.security.WebSecurityConfiguration;
import com.zerobase.zbcharger.configuration.security.filter.JwtAuthenticationFilter;
import com.zerobase.zbcharger.domain.member.entity.Member;
import com.zerobase.zbcharger.domain.membership.dto.IssueMembershipCardRequest;
import com.zerobase.zbcharger.domain.membership.dto.RegisterMembershipCardRequest;
import com.zerobase.zbcharger.domain.membership.entity.MembershipCard;
import com.zerobase.zbcharger.domain.membership.service.IssueMembershipService;
import com.zerobase.zbcharger.domain.membership.service.MembershipService;
import com.zerobase.zbcharger.domain.membership.service.RegisterMembershipService;
import com.zerobase.zbcharger.util.MockMvcUtils;
import com.zerobase.zbcharger.util.ResultActionsUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

@WebMvcTest(value = MembershipController.class,
    excludeAutoConfiguration = {SecurityAutoConfiguration.class},
    excludeFilters = {
        @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = {
            WebSecurityConfiguration.class,
            JwtAuthenticationFilter.class
        })
    })
class MembershipControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RegisterMembershipService registerMembershipService;

    @MockBean
    private IssueMembershipService issueMembershipService;

    @MockBean
    private MembershipService membershipService;

    private static final String MOCK_CARD_NUMBER = "1000-0000-0000-0001";
    private static final Member MOCK_MEMBER = Member.builder().build();
    private static final MembershipCard MOCK_MEMBERSHIP_CARD = MembershipCard.builder()
        .cardNo(MOCK_CARD_NUMBER)
        .member(MOCK_MEMBER)
        .cardType(PHYSICAL)
        .build();

    @Test
    @DisplayName("멤버십 카드 발급 성공")
    void successIssueMembershipCard() throws Exception {
        // given
        IssueMembershipCardRequest request = new IssueMembershipCardRequest(PHYSICAL.name());

        given(issueMembershipService.issueCard(any(Member.class), eq(request)))
            .willReturn(MOCK_MEMBERSHIP_CARD);

        // when
        ResultActions resultActions = MockMvcUtils.performPost(mockMvc, "/memberships/issue",
            request);

        // then
        resultActions.andExpect(status().isOk())
            .andExpect(jsonPath("$.cardNo").value(MOCK_CARD_NUMBER))
            .andExpect(jsonPath("$.cardType").value(PHYSICAL.name()));
    }

    @Test
    @DisplayName("멤버십 카드 발급 실패 - 카드 타입 유효성")
    void failIssueMembershipCard_cardType_invalid() throws Exception {
        // given
        IssueMembershipCardRequest request = new IssueMembershipCardRequest("invalid-card-type");

        // when
        ResultActions resultActions = MockMvcUtils.performPost(mockMvc, "/memberships/issue",
            request);

        // then
        ResultActionsUtils.expectArgumentNotValid(resultActions, "cardType", INVALID_CARD_TYPE);
    }

    @Test
    @DisplayName("멤버십 카드 등록 성공")
    void successRegisterMembershipCard() throws Exception {
        // given
        RegisterMembershipCardRequest request = new RegisterMembershipCardRequest(MOCK_CARD_NUMBER);

        // when
        ResultActions resultActions = MockMvcUtils.performPost(mockMvc, "/memberships/register",
            request);

        // then
        resultActions.andExpect(status().isNoContent());
        verify(registerMembershipService).registerCard(any(Member.class), eq(request));
    }

    @Test
    @DisplayName("멤버십 카드 등록 실패 - 카드번호 유효성")
    void failRegisterMembershipCard_invalid_cardNo() throws Exception {
        // given
        RegisterMembershipCardRequest request = new RegisterMembershipCardRequest("0000");

        // when
        ResultActions resultActions = MockMvcUtils.performPost(mockMvc, "/memberships/register",
            request);

        // then
        ResultActionsUtils.expectArgumentNotValid(resultActions, "cardNo",
            INVALID_MEMBERSHIP_CARD_FORMAT);
    }
}