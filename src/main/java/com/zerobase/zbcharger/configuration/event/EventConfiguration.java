package com.zerobase.zbcharger.configuration.event;

import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class EventConfiguration {

    private final ApplicationContext applicationContext;

//    @Bean
//    public InitializingBean eventsInitializer() {
//        return () -> EventPublisher.setPublisher(applicationContext);
//    }
}
