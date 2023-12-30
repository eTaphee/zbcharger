package com.zerobase.zbcharger.util;

import static com.zerobase.zbcharger.validator.constant.ValidationMessage.INVALID_EMAIL_FORMAT;
import static com.zerobase.zbcharger.validator.constant.ValidationMessage.INVALID_MEMBER_NAME_FORMAT;
import static com.zerobase.zbcharger.validator.constant.ValidationMessage.INVALID_PASSWORD_FORMAT;
import static com.zerobase.zbcharger.validator.constant.ValidationMessage.INVALID_TEL_FORMAT;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.zerobase.zbcharger.exception.constant.ErrorCode;
import org.springframework.test.web.servlet.ResultActions;

public class ResultActionsUtils {

    public static void expectArgumentNotValid(ResultActions resultActions, String field,
        String reason) throws Exception {
        resultActions.andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.status").value(400))
            .andExpect(jsonPath("$.code").value(ErrorCode.ARGUMENT_NOT_VALID.toString()))
            .andExpect(jsonPath("$.errors[*].field").value(field))
            .andExpect(jsonPath("$.errors[*].reason").value((reason)));
    }

    public static void expectArgumentNotValidForPassword(ResultActions resultActions)
        throws Exception {
        expectArgumentNotValid(resultActions, "password", INVALID_PASSWORD_FORMAT);
    }

    public static void expectArgumentNotValidForEmail(ResultActions resultActions)
        throws Exception {
        expectArgumentNotValid(resultActions, "email", INVALID_EMAIL_FORMAT);
    }

    public static void expectArgumentNotValidForMemberName(ResultActions resultActions)
        throws Exception {
        expectArgumentNotValid(resultActions, "name", INVALID_MEMBER_NAME_FORMAT);
    }

    public static void expectArgumentNotValidForPhone(ResultActions resultActions)
        throws Exception {
        expectArgumentNotValid(resultActions, "phone", INVALID_TEL_FORMAT);
    }
}
