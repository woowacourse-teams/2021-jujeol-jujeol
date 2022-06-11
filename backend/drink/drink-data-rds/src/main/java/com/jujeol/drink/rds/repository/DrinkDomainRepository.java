package com.jujeol.drink.rds.repository;

import com.jujeol.drink.domain.model.Category;
import com.jujeol.drink.domain.model.ImageFilePath;
import com.jujeol.drink.domain.usecase.DrinkRegisterUseCase;
import com.jujeol.drink.domain.usecase.command.DrinkPortCreateCommand;
import com.jujeol.drink.rds.entity.CategoryEntity;
import com.jujeol.drink.rds.entity.DrinkEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class DrinkDomainRepository implements DrinkRegisterUseCase.DrinkPort {

    private final DrinkRepository drinkRepository;

    @Override
    @Transactional
    public void createDrink(DrinkPortCreateCommand createCommand, Category category, ImageFilePath imageFilePath) {
        CategoryEntity categoryEntity = CategoryEntity.builder()
            .id(category.getId())
            .key(category.getKey())
            .name(category.getName())
            .build();

        DrinkEntity drinkEntity = DrinkEntity.builder()
            .description(createCommand.getDescription())
            .englishName(createCommand.getEnglishName())
            .name(createCommand.getName())
            .alcoholByVolume(createCommand.getAlcoholByVolume())
            .category(categoryEntity)
            .preferenceAvg(0.0)
            .largeImageFilePath(imageFilePath.getLargeImageFilePath())
            .mediumImageFilePath(imageFilePath.getMediumImageFilePath())
            .smallImageFilePath(imageFilePath.getSmallImageFilePath())
            .build();

        drinkRepository.save(drinkEntity);
    }
}
