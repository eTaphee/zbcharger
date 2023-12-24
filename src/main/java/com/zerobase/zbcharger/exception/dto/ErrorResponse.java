package com.zerobase.zbcharger.exception.dto;

import com.zerobase.zbcharger.exception.constant.ErrorCode;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import org.springframework.validation.method.ParameterValidationResult;

/**
 * 에러 응답
 */
@Getter
public class ErrorResponse {

    /**
     * 요청 경로
     */
    private final String path;

    /**
     * 상태
     */
    private final int status;

    /**
     * 메시지
     */
    private final String message;

    /**
     * 코드
     */
    private final ErrorCode code;

    /**
     * 필드 에러
     */
    private final List<Object> errors;

    private ErrorResponse(String path, ErrorCode code) {
        this.path = path;
        this.code = code;
        this.status = code.getStatus().value();
        this.message = code.getMessage();
        this.errors = new ArrayList<>();
    }

    private ErrorResponse(String path, ErrorCode code,
        List<org.springframework.validation.FieldError> errors) {
        this(path, code);

        if (errors != null) {
            this.errors.addAll(
                errors
                    .stream()
                    .map(m -> new FieldError(m.getField(), m.getDefaultMessage()))
                    .toList()
            );
        }
    }

//    private ErrorResponse(String path, ErrorCode code,
//        List<ParameterValidationResult> errors) {
//        this(path, code);
//
//        if (errors != null) {
////            this.errors.addAll(
////                errors
////                    .stream()
////                    .map(m -> new FieldError(m.getField(), m.getDefaultMessage()))
////                    .toList()
////            );
//        }
//    }

    public static ErrorResponse of(String path, ErrorCode code) {
        return new ErrorResponse(path, code);
    }

    public static ErrorResponse of(String path,
        ErrorCode code,
        List<org.springframework.validation.FieldError> errors) {
        return new ErrorResponse(path, code, errors);
    }

//    public static ErrorResponse of(String path,
//        ErrorCode code,
//        List<ParameterValidationResult> errors) {
//        return new ErrorResponse(path, code, errors);
//    }

    /**
     * 필드 에러
     */
    @Getter
    public static class FieldError {

        /**
         * 필드명
         */
        private final String field;

        /**
         * 에러 메시지
         */
        private final String reason;

        private FieldError(String field, String reason) {
            this.field = field;
            this.reason = reason;
        }
    }
}
