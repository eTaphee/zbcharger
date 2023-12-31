package com.zerobase.zbcharger.configuration.mvc;

import com.zerobase.zbcharger.domain.payment.dto.resolver.SmartroPayCallbackArgumentResolver;
import java.util.List;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfiguration implements WebMvcConfigurer {

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(new SmartroPayCallbackArgumentResolver());
    }
}
