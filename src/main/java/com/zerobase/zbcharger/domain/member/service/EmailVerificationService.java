package com.zerobase.zbcharger.domain.member.service;

import com.zerobase.zbcharger.domain.member.dao.EmailVerificationRepository;
import com.zerobase.zbcharger.domain.member.entity.EmailVerification;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.util.UriComponentsBuilder;

/**
 * 이메일 인증 서비스
 */
@Service
@RequiredArgsConstructor
public class EmailVerificationService {

    private final EmailVerificationRepository emailVerificationRepository;

    private final JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String from;

    @Value("${api.uri}")
    private String host;

    /**
     * 인증 이메일 전송
     *
     * @param id    인증 아이디
     * @param email 이메일
     */
    @Transactional
    public void sendVerificationEmail(UUID id, String email) {
        mailSender.send(createVerificationMessage(id, email));
    }

    /**
     * 인증 메시지 생성
     *
     * @param id    인증 아이디
     * @param email 이메일
     * @return 인증 메시지
     */
    private MimeMessage createVerificationMessage(UUID id,
        String email) {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, "UTF-8");

        try {
            helper.setFrom(from);
            helper.setTo(email);
            helper.setSubject("[ZB Charger] 이메일 인증");
            helper.setText(createVerificationUri(id, email));
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }

        return message;
    }

    /**
     * 인증 uri 생성
     *
     * @param id    인증 아이디
     * @param email 이메일
     * @return 인증 uri
     */
    private String createVerificationUri(UUID id, String email) {
        return UriComponentsBuilder.fromHttpUrl(host)
            .path("/member/email/verify")
            .queryParam("email", email)
            .queryParam("token", id)
            .build()
            .toString();
    }

    /**
     * 이메일 인증
     *
     * @param token 인증 아이디
     * @param email 이메일
     */
    @Transactional
    public void verifyEmail(UUID token, String email) {
        // TODO: 커스텀 예외
        EmailVerification emailVerification = emailVerificationRepository
            .findByIdAndMemberEmail(token, email)
            .orElseThrow(() -> new RuntimeException("not found"));

        emailVerification.verify();
    }
}
