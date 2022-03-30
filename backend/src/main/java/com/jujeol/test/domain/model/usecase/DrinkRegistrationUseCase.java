package com.jujeol.test.domain.model.usecase;

import com.jujeol.drink.drink.domain.Drink;
import com.jujeol.test.domain.model.command.DrinkCommand;

import java.util.List;

public interface DrinkRegistrationUseCase {

    void register(DrinkCommand drinkCommand);

    interface DrinkInsertPort {
        void insert();
    }

    interface DrinkSearchPort {
        List<Drink> searchDrinks();
    }
}
