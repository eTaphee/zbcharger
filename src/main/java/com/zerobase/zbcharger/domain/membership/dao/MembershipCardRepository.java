package com.zerobase.zbcharger.domain.membership.dao;

import com.zerobase.zbcharger.domain.member.entity.Member;
import com.zerobase.zbcharger.domain.membership.entity.CardType;
import com.zerobase.zbcharger.domain.membership.entity.MembershipCard;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MembershipCardRepository extends JpaRepository<MembershipCard, String> {

    List<MembershipCard> findAllByMemberId(Long memberId);

    boolean existsByMemberAndCardType(Member member, CardType cardType);

    long countByCardType(CardType cardType);
}
