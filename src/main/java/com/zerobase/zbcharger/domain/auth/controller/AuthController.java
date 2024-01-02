package com.zerobase.zbcharger.domain.auth.controller;

import com.zerobase.zbcharger.domain.auth.dto.AuthenticationDto;
import com.zerobase.zbcharger.domain.auth.dto.GenerateTokenRequest;
import com.zerobase.zbcharger.domain.auth.dto.GenerateTokenResponse;
import com.zerobase.zbcharger.domain.auth.service.AuthService;
import com.zerobase.zbcharger.domain.auth.service.TokenService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    private final TokenService tokenService;

    /**
     * 토큰 요청
     *
     * @param request 로그인 정보
     * @return 토큰
     */
    @PostMapping("/auth/token")
    public ResponseEntity<GenerateTokenResponse> generateToken(
        @Valid @RequestBody GenerateTokenRequest request) {
        AuthenticationDto authentication = authService.authenticate(request);
        return ResponseEntity.ok(
            GenerateTokenResponse.from(tokenService.generateToken(authentication)));
    }
}
