package com.jujeol.drink.domain.usecase;

import com.jujeol.drink.domain.usecase.command.DrinkPreferenceUpdateCommand;

public interface DrinkPreferenceUpdateUseCase {

    void updateDrinkPreference(DrinkPreferenceUpdateCommand command);

    interface DrinkPort {
        void updatePreference(Long drinkId, Double preferenceAvg);
    }
}
