package com.jujeol.feedback.domain.event;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class PreferenceEvent {

    private EventType eventType;
    private double rate;
    private Long drinkId;

    public static PreferenceEvent create(EventType eventType, double rate, Long drinkId) {
        return new PreferenceEvent(eventType, rate, drinkId);
    }

    public enum EventType {
        CREATE, UPDATE, DELETE
    }
}
