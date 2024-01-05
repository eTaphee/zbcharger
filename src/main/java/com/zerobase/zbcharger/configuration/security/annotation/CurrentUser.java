package com.zerobase.zbcharger.configuration.security.annotation;

import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import org.springframework.security.core.annotation.AuthenticationPrincipal;

@Target({PARAMETER})
@Retention(RUNTIME)
@AuthenticationPrincipal(expression = "member")
public @interface CurrentUser {

}
