package com.zerobase.zbcharger.validator.annotation;

import com.zerobase.zbcharger.validator.PhoneValidator;
import com.zerobase.zbcharger.validator.constant.ValidationMessage;
import jakarta.validation.Constraint;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = PhoneValidator.class)
public @interface Phone {

    String message() default ValidationMessage.INVALID_TEL_FORMAT;

    Class[] groups() default {};

    Class[] payload() default {};
}
