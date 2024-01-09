package com.zerobase.zbcharger.domain.member.dao;

import com.zerobase.zbcharger.domain.member.entity.Member;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {

    boolean existsByEmail(String email);

    Optional<Member> findByEmail(String email);

    @Query("select m.id from Member m where m.email = :email")
    Optional<Long> findIdByEmail(String email);
}
