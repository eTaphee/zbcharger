package com.zerobase.zbcharger.domain.auth.controller;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.zerobase.zbcharger.configuration.security.WebSecurityConfiguration;
import com.zerobase.zbcharger.configuration.security.filter.JwtAuthenticationFilter;
import com.zerobase.zbcharger.domain.auth.dto.AuthenticationDto;
import com.zerobase.zbcharger.domain.auth.dto.GenerateTokenRequest;
import com.zerobase.zbcharger.domain.auth.dto.TokenDto;
import com.zerobase.zbcharger.domain.auth.service.AuthService;
import com.zerobase.zbcharger.domain.auth.service.TokenService;
import com.zerobase.zbcharger.domain.member.entity.Role;
import com.zerobase.zbcharger.util.MockMvcUtils;
import com.zerobase.zbcharger.util.ResultActionsUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

@WebMvcTest(value = AuthController.class,
    excludeAutoConfiguration = {SecurityAutoConfiguration.class},
    excludeFilters = {
        @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = {
            WebSecurityConfiguration.class,
            JwtAuthenticationFilter.class
        })
    })
class AuthControllerTest {

    private static final String TOKEN_URL = "/auth/token";
    private static final String MOCK_EMAIL = "member@zbcharger.com";
    private static final String MOCK_PASSWORD = "1q2w#E$R";
    private static final String MOCK_ACCESS_TOKEN = "accessToken";
    private static final String MOCK_REFRESH_TOKEN = "refreshToken";

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AuthService authService;

    @MockBean
    private TokenService tokenService;

    @Test
    @DisplayName("토큰 발급 성공")
    void successGenerateToken() throws Exception {
        // given
        GenerateTokenRequest request = new GenerateTokenRequest(MOCK_EMAIL, MOCK_PASSWORD);
        AuthenticationDto authentication = new AuthenticationDto(MOCK_EMAIL, Role.USER);
        TokenDto token = new TokenDto(MOCK_ACCESS_TOKEN, MOCK_REFRESH_TOKEN);

        given(authService.authenticate(request))
            .willReturn(authentication);

        given(tokenService.generateToken(authentication))
            .willReturn(token);

        // when
        ResultActions resultActions = MockMvcUtils.performPost(mockMvc,
            TOKEN_URL,
            request);

        // then
        resultActions.andExpect(status().isOk())
            .andExpect(jsonPath("$.accessToken").value(MOCK_ACCESS_TOKEN))
            .andExpect(jsonPath("$.refreshToken").value(MOCK_REFRESH_TOKEN));
    }

    @Test
    @DisplayName("토큰 발급 실패 - 이메일 유효성")
    void failGenerateToken_email_invalid() throws Exception {
        // given
        GenerateTokenRequest request = new GenerateTokenRequest("invalid-email", MOCK_PASSWORD);

        // when
        ResultActions resultActions = MockMvcUtils.performPost(mockMvc,
            TOKEN_URL,
            request);

        // then
        ResultActionsUtils.expectArgumentNotValidForEmail(resultActions);
    }

    @Test
    @DisplayName("토큰 발급 실패 - 패스워드 길이 8 미만")
    void failGenerateToken_password_length_less_8() throws Exception {
        // given
        GenerateTokenRequest request = new GenerateTokenRequest(MOCK_EMAIL, "1q2w#E3");

        // when
        ResultActions resultActions = MockMvcUtils.performPost(mockMvc,
            TOKEN_URL,
            request);

        // then
        ResultActionsUtils.expectArgumentNotValidForPassword(resultActions);
    }

    @Test
    @DisplayName("토큰 발급 실패 - 패스워드 길이 20 이상")
    void failGenerateToken_password_length_grater_than_20() throws Exception {
        // given
        GenerateTokenRequest request = new GenerateTokenRequest(MOCK_EMAIL,
            "1q2w3e4r5t!Q@W#E$R%T1q");

        // when
        ResultActions resultActions = MockMvcUtils.performPost(mockMvc,
            TOKEN_URL,
            request);

        // then
        ResultActionsUtils.expectArgumentNotValidForPassword(resultActions);
    }

    @Test
    @DisplayName("토큰 발급 실패 - 패스워드 유효성(소문자 없음)")
    void failGenerateToken_password_not_include_lowercase() throws Exception {
        // given
        GenerateTokenRequest request = new GenerateTokenRequest(MOCK_EMAIL, "1Q2W3E$R");

        // when
        ResultActions resultActions = MockMvcUtils.performPost(mockMvc,
            TOKEN_URL,
            request);

        // then
        ResultActionsUtils.expectArgumentNotValidForPassword(resultActions);
    }

    @Test
    @DisplayName("토큰 발급 실패 - 패스워드 유효성(대문자 없음)")
    void failGenerateToken_password_not_include_uppercase() throws Exception {
        // given
        GenerateTokenRequest request = new GenerateTokenRequest(MOCK_EMAIL, "1q2w3e$r");

        // when
        ResultActions resultActions = MockMvcUtils.performPost(mockMvc,
            TOKEN_URL,
            request);

        // then
        ResultActionsUtils.expectArgumentNotValidForPassword(resultActions);
    }

    @Test
    @DisplayName("토큰 발급 실패 - 패스워드 유효성(숫자 없음)")
    void failGenerateToken_password_not_include_number() throws Exception {
        // given
        GenerateTokenRequest request = new GenerateTokenRequest(MOCK_EMAIL, "!q@w#e$r");

        // when
        ResultActions resultActions = MockMvcUtils.performPost(mockMvc,
            TOKEN_URL,
            request);

        // then
        ResultActionsUtils.expectArgumentNotValidForPassword(resultActions);
    }

    @Test
    @DisplayName("토큰 발급 실패 - 패스워드 유효성(특수문자 없음)")
    void failGenerateToken_password_not_include_special_character() throws Exception {
        // given
        GenerateTokenRequest request = new GenerateTokenRequest(MOCK_EMAIL, "1q2w3e4r");

        // when
        ResultActions resultActions = MockMvcUtils.performPost(mockMvc,
            TOKEN_URL,
            request);

        // then
        ResultActionsUtils.expectArgumentNotValidForPassword(resultActions);
    }
}