package com.jujeol.drink.rds.repository;

import com.jujeol.drink.domain.exception.NotFoundDrinkException;
import com.jujeol.drink.domain.model.Category;
import com.jujeol.drink.domain.model.Drink;
import com.jujeol.drink.domain.model.ImageFilePath;
import com.jujeol.drink.domain.reader.DrinkReader;
import com.jujeol.drink.domain.usecase.DrinkPreferenceUpdateUseCase;
import com.jujeol.drink.domain.usecase.DrinkRegisterUseCase;
import com.jujeol.drink.domain.usecase.command.DrinkPortCreateCommand;
import com.jujeol.drink.rds.entity.CategoryEntity;
import com.jujeol.drink.rds.entity.DrinkEntity;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class DrinkDomainRepository implements
    DrinkReader,

    DrinkRegisterUseCase.DrinkPort,
        DrinkPreferenceUpdateUseCase.DrinkPort
{

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

    @Override
    @Transactional(readOnly = true)
    public Optional<Drink> findById(Long id) {
        return drinkRepository.findById(id).map(DrinkEntity::toDomain);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Drink> findAllByDrinkIdIn(Collection<Long> drinkIds) {
        return drinkRepository.findAllByIdIn(drinkIds).stream().map(DrinkEntity::toDomain).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void updatePreference(Long drinkId, Double preferenceAvg) {
        DrinkEntity drinkEntity = drinkRepository.findById(drinkId).orElseThrow(NotFoundDrinkException::new);
        drinkEntity.changePreferenceAvg(preferenceAvg);
    }
}
