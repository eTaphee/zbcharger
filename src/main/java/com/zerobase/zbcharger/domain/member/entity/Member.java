package com.zerobase.zbcharger.domain.member.entity;

import com.zerobase.zbcharger.domain.common.entity.AuditableEntity;
import com.zerobase.zbcharger.util.ValidationUtils;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 회원
 */
@Entity
@Getter
@NoArgsConstructor(force = true, access = AccessLevel.PROTECTED)
public class Member extends AuditableEntity {

    /**
     * pk
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 이메일
     */
    private final String email;

    /**
     * 패스워드
     */
    private String password;

    /**
     * 이름
     */
    private final String name;

    /**
     * 연락처
     */
    private String tel;

    /**
     * 이메일 인증 일시
     */
    private LocalDateTime emailVerifiedAt;

    @Builder
    public Member(String email, String password, String name, String tel) {
        validateMemberInitialize(email, password, name, tel);
        this.email = email;
        this.password = password;
        this.name = name;
        this.tel = tel;
    }

    /**
     * 회원 생성자 유효성 검사
     *
     * @param email    이메일
     * @param password 패스워드
     * @param name     이름
     * @param tel      연락처
     */
    private void validateMemberInitialize(String email, String password, String name, String tel) {
        ValidationUtils.StringMustBeNotBlank("email", email);
        ValidationUtils.StringMustBeNotBlank("password", password);
        ValidationUtils.StringMustBeNotBlank("name", name);
        ValidationUtils.StringMustBeNotBlank("tel", tel);
    }
}
