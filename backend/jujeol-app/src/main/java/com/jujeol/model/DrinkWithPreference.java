package com.jujeol.model;

import com.jujeol.drink.domain.model.Drink;
import com.jujeol.feedback.domain.model.Preference;
import lombok.Getter;

@Getter
public class DrinkWithPreference {

    private Drink drink;
    private Preference preference;

    private DrinkWithPreference(Drink drink, Preference preference) {
        this.drink = drink;
        this.preference = preference;
    }

    public static DrinkWithPreference create(Drink drink, Preference preference) {
        return new DrinkWithPreference(drink, preference);
    }
}
