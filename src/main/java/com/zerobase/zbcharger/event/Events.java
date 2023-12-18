package com.zerobase.zbcharger.event;

import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class Events {

    private final ApplicationEventPublisher publisher;

    public void raise(Object event) {
        if (publisher != null) {
            publisher.publishEvent(event);
        }
    }
}
