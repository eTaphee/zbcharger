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
     * @param memberId 회원 아이디
     * @param email    이메일
     */
    @Transactional
    public void sendVerificationEmail(Long memberId, String email) {
        EmailVerification emailVerification
            = emailVerificationRepository.save(new EmailVerification(memberId));

        // 이메일 전송
        mailSender.send(createVerificationMessage(emailVerification, email));
    }

    /**
     * 인증 메시지 생성
     *
     * @param emailVerification 인증정보
     * @param email             이메일
     * @return 인증 메시지
     */
    private MimeMessage createVerificationMessage(EmailVerification emailVerification,
        String email) {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, "UTF-8");

        try {
            helper.setFrom(from);
            helper.setTo(email);
            helper.setSubject("[ZB Charger] 이메일 인증");
            helper.setText(createVerificationUri(email, emailVerification.getId()));
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }

        return message;
    }

    /**
     * 인증 uri 생성
     *
     * @param email 이메일
     * @param token 이메일 인증 pk
     * @return 인증 uri
     */
    private String createVerificationUri(String email, UUID token) {
        return UriComponentsBuilder.fromHttpUrl(host)
            .path("/member/email/verify")
            .queryParam("email", email)
            .queryParam("token", token)
            .build()
            .toString();
    }
}
