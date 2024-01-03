package com.zerobase.zbcharger.domain.membership.controller;

import com.zerobase.zbcharger.configuration.security.annotation.CurrentUser;
import com.zerobase.zbcharger.configuration.security.annotation.RoleUser;
import com.zerobase.zbcharger.domain.member.entity.Member;
import com.zerobase.zbcharger.domain.membership.dto.IssueMembershipCardRequest;
import com.zerobase.zbcharger.domain.membership.dto.IssueMembershipCardResponse;
import com.zerobase.zbcharger.domain.membership.dto.MembershipCardDto;
import com.zerobase.zbcharger.domain.membership.dto.RegisterMembershipCardRequest;
import com.zerobase.zbcharger.domain.membership.entity.MembershipCard;
import com.zerobase.zbcharger.domain.membership.service.IssueMembershipService;
import com.zerobase.zbcharger.domain.membership.service.MembershipService;
import com.zerobase.zbcharger.domain.membership.service.RegisterMembershipService;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * 멤버십 컨트롤러
 */
@RoleUser
@RestController
@RequiredArgsConstructor
public class MembershipController {

    private final IssueMembershipService issueMembershipService;
    private final RegisterMembershipService registerMembershipService;
    private final MembershipService membershipService;

    /**
     * 멤버십 카드 발급 신청
     */
    @PostMapping("/memberships/issue")
    public ResponseEntity<IssueMembershipCardResponse> issueCard(
        @Valid @RequestBody IssueMembershipCardRequest request,
        @CurrentUser Member member) {
        MembershipCard membershipCard = issueMembershipService.issueCard(member, request);
        return ResponseEntity.ok(IssueMembershipCardResponse.from(membershipCard));
    }

    /**
     * 멤버십 카드 등록
     */
    @PostMapping("/memberships/register")
    public ResponseEntity<Void> registerCard(
        @Valid @RequestBody RegisterMembershipCardRequest request,
        @CurrentUser Member member) {
        registerMembershipService.registerCard(member, request);
        return ResponseEntity.noContent().build();
    }

    /**
     * 멤버십 카드 조회
     */
    @GetMapping("/memberships")
    public ResponseEntity<List<MembershipCardDto>> getCards(@CurrentUser Member member) {
        return ResponseEntity.ok(
            membershipService.getCards(member)
                .stream()
                .map(MembershipCardDto::from)
                .toList()
        );
    }
}
