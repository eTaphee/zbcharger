package com.zerobase.zbcharger.domain.member.dao;

import com.zerobase.zbcharger.domain.member.entity.EmailVerification;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmailVerificationRepository extends JpaRepository<EmailVerification, UUID> {

    Optional<EmailVerification> findByIdAndMemberEmail(UUID id, String email);

    Optional<EmailVerification> findByMemberEmail(String email);
}
