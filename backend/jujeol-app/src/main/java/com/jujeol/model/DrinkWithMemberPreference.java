package com.jujeol.model;

import com.jujeol.drink.domain.model.Drink;
import com.jujeol.feedback.domain.model.Preference;
import lombok.Getter;

@Getter
public class DrinkWithMemberPreference {

    private final Drink drink;

    // preference 를 남기지 않는다면 0으로 등록이 된다.
    private final Preference preference;

    // 이미 preference 가 존재하면 0 으로 전달한다.
    private final Double expectedPreference;

    public DrinkWithMemberPreference(Drink drink, Preference preference, Double expectedPreference) {
        this.drink = drink;
        this.preference = preference;
        this.expectedPreference = expectedPreference;
    }

    public static DrinkWithMemberPreference create(Drink drink, Preference preference, Double expectedPreference) {
        return new DrinkWithMemberPreference(drink, preference, expectedPreference);
    }
}
