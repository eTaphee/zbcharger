package com.zerobase.zbcharger.domain.member.controller;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.zerobase.zbcharger.domain.member.dto.RegisterMemberRequest;
import com.zerobase.zbcharger.domain.member.service.RegisterMemberService;
import com.zerobase.zbcharger.util.MockMvcUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

@WebMvcTest(MemberController.class)
@AutoConfigureMockMvc(addFilters = false)
class MemberControllerTest {

    private static final String REGISTER_URL = "/members/register";
    private static final String MOCK_EMAIL = "member@zbcharger.com";
    private static final String MOCK_PASSWORD = "1q2w#E$R";
    private static final String MOCK_NAME = "이태희";
    private static final String MOCK_PHONE = "010-1234-5678";

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RegisterMemberService registerMemberService;

    @Test
    @DisplayName("회원가입 성공")
    void successRegisterMember() throws Exception {
        // given
        // when
        RegisterMemberRequest request = new RegisterMemberRequest(
            MOCK_EMAIL,
            MOCK_PASSWORD,
            MOCK_NAME,
            MOCK_PHONE);

        ResultActions resultActions = MockMvcUtils.performPost(mockMvc,
            REGISTER_URL,
            request);

        // then
        resultActions.andExpect(status().isOk());
    }

    @Test
    @DisplayName("회원가입 실패 - 이메일 null")
    void failRegisterMember_email_null() throws Exception {
        // given
        // when
        RegisterMemberRequest request = new RegisterMemberRequest(
            null,
            MOCK_PASSWORD,
            MOCK_NAME,
            MOCK_PHONE);

        ResultActions resultActions = MockMvcUtils.performPost(mockMvc,
            REGISTER_URL,
            request);

        // then
        resultActions.andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("회원가입 실패 - 이메일 유효성")
    void failRegisterMember_email_invalid() throws Exception {
        // given
        // when
        RegisterMemberRequest request = new RegisterMemberRequest(
            "invalid-email",
            MOCK_PASSWORD,
            MOCK_NAME,
            MOCK_PHONE);

        ResultActions resultActions = MockMvcUtils.performPost(mockMvc,
            REGISTER_URL,
            request);

        // then
        resultActions.andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("회원가입 실패 - 패스워드 길이 8 미만")
    void failRegisterMember_password_length_less_8() throws Exception {
        // given
        // when
        RegisterMemberRequest request = new RegisterMemberRequest(
            MOCK_EMAIL,
            "1q2w#E3",
            MOCK_NAME,
            MOCK_PHONE);

        ResultActions resultActions = MockMvcUtils.performPost(mockMvc,
            REGISTER_URL,
            request);

        // then
        resultActions.andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("회원가입 실패 - 패스워드 길이 20 이상")
    void failRegisterMember_password_length_grater_than_20() throws Exception {
        // given
        // when
        RegisterMemberRequest request = new RegisterMemberRequest(
            MOCK_EMAIL,
            "1q2w3e4r5t!Q@W#E$R%T1q",
            MOCK_NAME,
            MOCK_PHONE);

        ResultActions resultActions = MockMvcUtils.performPost(mockMvc,
            REGISTER_URL,
            request);

        // then
        resultActions.andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("회원가입 실패 - 패스워드 유효성(소문자 없음)")
    void failRegisterMember_password_not_include_lowercase() throws Exception {
        // given
        // when
        RegisterMemberRequest request = new RegisterMemberRequest(
            MOCK_EMAIL,
            "1Q2W3E$R",
            MOCK_NAME,
            MOCK_PHONE);

        ResultActions resultActions = MockMvcUtils.performPost(mockMvc,
            REGISTER_URL,
            request);

        // then
        resultActions.andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("회원가입 실패 - 패스워드 유효성(대문자 없음)")
    void failRegisterMember_password_not_include_uppercase() throws Exception {
        // given
        // when
        RegisterMemberRequest request = new RegisterMemberRequest(
            MOCK_EMAIL,
            "1q2w3e$r",
            MOCK_NAME,
            MOCK_PHONE);

        ResultActions resultActions = MockMvcUtils.performPost(mockMvc,
            REGISTER_URL,
            request);

        // then
        resultActions.andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("회원가입 실패 - 패스워드 유효성(숫자 없음)")
    void failRegisterMember_password_not_include_number() throws Exception {
        // given
        // when
        RegisterMemberRequest request = new RegisterMemberRequest(
            MOCK_EMAIL,
            "!q@w#e$r",
            MOCK_NAME,
            MOCK_PHONE);

        ResultActions resultActions = MockMvcUtils.performPost(mockMvc,
            REGISTER_URL,
            request);

        // then
        resultActions.andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("회원가입 실패 - 패스워드 유효성(특수문자 없음)")
    void failRegisterMember_password_not_include_special_character() throws Exception {
        // given
        // when
        RegisterMemberRequest request = new RegisterMemberRequest(
            MOCK_EMAIL,
            "1q2w3e4r",
            MOCK_NAME,
            MOCK_PHONE);

        ResultActions resultActions = MockMvcUtils.performPost(mockMvc,
            REGISTER_URL,
            request);

        // then
        resultActions.andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("회원가입 실패 - 이름 빈값(white space)")
    void failRegisterMember_name_blank_white_space() throws Exception {
        // given
        // when
        RegisterMemberRequest request = new RegisterMemberRequest(
            MOCK_EMAIL,
            MOCK_PASSWORD,
            "  ",
            MOCK_PHONE);

        ResultActions resultActions = MockMvcUtils.performPost(mockMvc,
            REGISTER_URL,
            request);

        // then
        resultActions.andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("회원가입 실패 - 이름 빈값(null)")
    void failRegisterMember_name_blank_null() throws Exception {
        // given
        // when
        RegisterMemberRequest request = new RegisterMemberRequest(
            MOCK_EMAIL,
            MOCK_PASSWORD,
            null,
            MOCK_PHONE);

        ResultActions resultActions = MockMvcUtils.performPost(mockMvc,
            REGISTER_URL,
            request);

        // then
        resultActions.andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("회원가입 실패 - 이름 3자리 미만")
    void failRegisterMember_name_length_less_than_3() throws Exception {
        // given
        // when
        RegisterMemberRequest request = new RegisterMemberRequest(
            MOCK_EMAIL,
            MOCK_PASSWORD,
            "이태",
            MOCK_PHONE);

        ResultActions resultActions = MockMvcUtils.performPost(mockMvc,
            REGISTER_URL,
            request);

        // then
        resultActions.andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("회원가입 실패 - 이름 10자리 초과")
    void failRegisterMember_name_length_grater_than_10() throws Exception {
        // given
        // when
        RegisterMemberRequest request = new RegisterMemberRequest(
            MOCK_EMAIL,
            MOCK_PASSWORD,
            "킹갓제네럴마제스티이태희",
            MOCK_PHONE);

        ResultActions resultActions = MockMvcUtils.performPost(mockMvc,
            REGISTER_URL,
            request);

        // then
        resultActions.andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("회원가입 실패 - 핸드폰 null")
    void failRegisterMember_phone_null() throws Exception {
        // given
        // when
        RegisterMemberRequest request = new RegisterMemberRequest(
            MOCK_EMAIL,
            MOCK_PASSWORD,
            MOCK_NAME,
            null);

        ResultActions resultActions = MockMvcUtils.performPost(mockMvc,
            REGISTER_URL,
            request);

        // then
        resultActions.andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("회원가입 실패 - 핸드폰 유효성")
    void failRegisterMember_phone_invalid() throws Exception {
        // given
        // when
        RegisterMemberRequest request = new RegisterMemberRequest(
            MOCK_EMAIL,
            MOCK_PASSWORD,
            MOCK_NAME,
            "02-1234-56617");

        ResultActions resultActions = MockMvcUtils.performPost(mockMvc,
            REGISTER_URL,
            request);

        // then
        resultActions.andExpect(status().isBadRequest());
    }
}