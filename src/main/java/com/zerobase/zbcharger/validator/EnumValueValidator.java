package com.zerobase.zbcharger.validator;

import com.zerobase.zbcharger.validator.annotation.EnumValue;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class EnumValueValidator implements ConstraintValidator<EnumValue, String> {

    private EnumValue enumValue;

    @Override
    public void initialize(EnumValue constraintAnnotation) {
        this.enumValue = constraintAnnotation;
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null) {
            return false;
        }

        Enum<?>[] enumValues = this.enumValue.enumClass().getEnumConstants();

        if (enumValues != null) {
            for (Object enumValue : enumValues) {
                if (value.equals(enumValue.toString())
                    || this.enumValue.ignoreCase() && value.equalsIgnoreCase(
                    enumValue.toString())) {
                    return true;
                }
            }
        }

        return false;
    }
}

