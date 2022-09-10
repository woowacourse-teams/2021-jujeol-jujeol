package com.jujeol.drink.domain.usecase.command;

import lombok.Builder;
import lombok.Getter;

@Getter
public class DrinkPreferenceUpdateCommand {

    private final Long drinkId;
    private final Double preferenceAvg;

    @Builder
    public DrinkPreferenceUpdateCommand(Long drinkId, Double preferenceAvg) {
        this.drinkId = drinkId;
        this.preferenceAvg = preferenceAvg;
    }
}
