package com.zerobase.zbcharger.validator.annotation;

import com.zerobase.zbcharger.validator.constant.ValidationMessage;
import jakarta.validation.Constraint;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@NotNull(message = ValidationMessage.INVALID_MEMBER_NAME_FORMAT)
@Size(min = 3, max = 10, message = ValidationMessage.INVALID_MEMBER_NAME_FORMAT)
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {})
public @interface MemberName {

    String message() default "";

    Class[] groups() default {};

    Class[] payload() default {};
}
