package com.jujeol.feedback.domain.model;

import lombok.Getter;

@Getter
public class Preference {

    private final Long id;
    private final Long memberId;
    private final Long drinkId;
    private final double rate;

    private Preference(Long id, Long memberId, Long drinkId, double rate) {
        this.id = id;
        this.memberId = memberId;
        this.drinkId = drinkId;
        this.rate = rate;
    }

    public static Preference create(Long id, Long memberId, Long drinkId, double rate) {
        return new Preference(id, memberId, drinkId, rate);
    }
}
