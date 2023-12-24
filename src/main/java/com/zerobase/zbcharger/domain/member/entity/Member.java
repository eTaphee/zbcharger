package com.zerobase.zbcharger.domain.member.entity;

import com.zerobase.zbcharger.domain.common.entity.AuditableEntity;
import com.zerobase.zbcharger.util.ValidationUtils;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
    private String phone;

    @Builder
    public Member(String email, String password, String name, String phone) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.phone = phone;
    }
}
