package com.zerobase.zbcharger.validator;

import com.zerobase.zbcharger.validator.annotation.MembershipCard;
import com.zerobase.zbcharger.validator.constant.RegexPattern;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class MembershipCardValidator implements ConstraintValidator<MembershipCard, String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null) {
            return false;
        }

        return value.matches(RegexPattern.MEMBERSHIP_CARD);
    }
}
