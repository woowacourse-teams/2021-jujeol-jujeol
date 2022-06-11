package com.jujeol.drink.domain.service;

import com.jujeol.drink.domain.exception.NotFoundCategoryException;
import com.jujeol.drink.domain.model.Category;
import com.jujeol.drink.domain.usecase.DrinkRegisterUseCase;
import com.jujeol.drink.domain.usecase.command.DrinkPortCreateCommand;
import com.jujeol.drink.domain.usecase.command.DrinkRegisterCommand;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class DrinkCommandExecutor implements DrinkRegisterUseCase {

    private final DrinkRegisterUseCase.DrinkPort drinkPort;

    private final DrinkRegisterUseCase.CategoryPort categoryPort;

    @Override
    @Transactional
    public void register(DrinkRegisterCommand command) throws NotFoundCategoryException {
        Category category = categoryPort.findByKey(command.getCategoryKey())
            .orElseThrow(NotFoundCategoryException::new);

        DrinkPortCreateCommand drinkPortCreateCommand = DrinkPortCreateCommand.create(command.getName().value(), command.getEnglishName().value(), command.getAlcoholByVolume().value(), command.getDescription().value());
        drinkPort.createDrink(drinkPortCreateCommand, category, command.getImageFilePath());
    }
}
