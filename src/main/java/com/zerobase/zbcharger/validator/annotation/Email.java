package com.zerobase.zbcharger.validator.annotation;

import com.zerobase.zbcharger.validator.EmailValidator;
import com.zerobase.zbcharger.validator.constant.ValidationMessage;
import jakarta.validation.Constraint;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = EmailValidator.class)
public @interface Email {

    String message() default ValidationMessage.INVALID_EMAIL_FORMAT;

    Class[] groups() default {};

    Class[] payload() default {};
}
