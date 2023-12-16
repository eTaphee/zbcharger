package com.zerobase.zbcharger.event;

import lombok.Getter;

@Getter
public abstract class Event {

    private final long timestamp;

    public Event() {
        timestamp = System.currentTimeMillis();
    }
}
