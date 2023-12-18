package com.zerobase.zbcharger.validator;

import com.zerobase.zbcharger.validator.annotation.Password;
import com.zerobase.zbcharger.validator.constant.RegexPattern;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PasswordValidator implements ConstraintValidator<Password, String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null) {
            return false;
        }

        return value.matches(RegexPattern.PASSWORD);
    }
}
