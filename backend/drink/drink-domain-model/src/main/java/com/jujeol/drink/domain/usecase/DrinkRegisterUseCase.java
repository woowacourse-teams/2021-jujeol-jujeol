package com.jujeol.drink.domain.usecase;

import com.jujeol.drink.domain.exception.NotFoundCategoryException;
import com.jujeol.drink.domain.model.ImageFilePath;
import com.jujeol.drink.domain.model.Category;
import com.jujeol.drink.domain.usecase.command.DrinkPortCreateCommand;
import com.jujeol.drink.domain.usecase.command.DrinkRegisterCommand;

import java.util.Optional;

public interface DrinkRegisterUseCase {

    void register(DrinkRegisterCommand command) throws NotFoundCategoryException;

    interface CategoryPort {
        Optional<Category> findByKey(String key);
    }

    interface DrinkPort {
        void createDrink(DrinkPortCreateCommand createCommand, Category category, ImageFilePath imageFilePath);
    }
}
