package com.jujeol.model;

import com.jujeol.drink.domain.model.Drink;
import com.jujeol.feedback.domain.model.Preference;
import lombok.Getter;

@Getter
public class PreferenceWithDrink {

    private final Drink drink;
    private final Preference preference;

    private PreferenceWithDrink(Drink drink, Preference preference) {
        this.drink = drink;
        this.preference = preference;
    }

    public static PreferenceWithDrink create(Drink drink, Preference preference) {
        return new PreferenceWithDrink(drink, preference);
    }
}
