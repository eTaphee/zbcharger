package com.zerobase.zbcharger.domain.membership.service;

import com.zerobase.zbcharger.domain.member.entity.Member;
import com.zerobase.zbcharger.domain.membership.dao.MembershipCardRepository;
import com.zerobase.zbcharger.domain.membership.entity.MembershipCard;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MembershipService {

    private final MembershipCardRepository membershipCardRepository;

    @Transactional(readOnly = true)
    public List<MembershipCard> getCards(Member member) {
        return membershipCardRepository.findAllByMemberId(member.getId());
    }
}
