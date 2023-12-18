package com.zerobase.zbcharger.validator;

import com.zerobase.zbcharger.validator.annotation.Email;
import com.zerobase.zbcharger.validator.constant.RegexPattern;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class EmailValidator implements ConstraintValidator<Email, String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null) {
            return false;
        }

        return value.matches(RegexPattern.EMAIL);
    }
}
