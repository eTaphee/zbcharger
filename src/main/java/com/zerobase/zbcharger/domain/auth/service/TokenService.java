package com.zerobase.zbcharger.domain.auth.service;

import com.zerobase.zbcharger.configuration.security.jwt.JwtTokenProvider;
import com.zerobase.zbcharger.domain.auth.dao.TokenRepository;
import com.zerobase.zbcharger.domain.auth.dto.AuthenticationDto;
import com.zerobase.zbcharger.domain.auth.dto.TokenDto;
import com.zerobase.zbcharger.domain.auth.entity.Token;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TokenService {

    private final JwtTokenProvider tokenProvider;
    private final TokenRepository tokenRepository;

    //    @Transactional
    public TokenDto generateToken(AuthenticationDto authentication) {
        Token token = tokenProvider.generateToken(authentication);
        tokenRepository.save(token);
        return TokenDto.fromEntity(token);
    }
}
