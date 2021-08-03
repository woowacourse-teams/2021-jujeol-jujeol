package com.jujeol.drink.infrastructure.recommend;

import org.apache.mahout.cf.taste.model.Preference;

public class TestPreference implements Preference {

    private Long userId;
    private Long itemId;
    private Float value;

    public TestPreference(com.jujeol.member.domain.Preference preference) {
        this.userId = preference.getMember().getId();
        this.itemId = preference.getDrink().getId();
        this.value = (float) preference.getRate();
    }

    @Override
    public long getUserID() {
        return userId;
    }

    @Override
    public long getItemID() {
        return itemId;
    }

    @Override
    public float getValue() {
        return value;
    }

    @Override
    public void setValue(float value) {
        this.value = value;
    }
}
