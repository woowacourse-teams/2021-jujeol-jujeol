package com.jujeol.drink.rds.repository;

import com.jujeol.drink.RdsTestContext;
import com.jujeol.drink.domain.model.Category;
import com.jujeol.drink.domain.model.ImageFilePath;
import com.jujeol.drink.domain.usecase.command.DrinkPortCreateCommand;
import com.jujeol.drink.rds.entity.CategoryEntity;
import com.jujeol.drink.rds.entity.DrinkEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;

class DrinkDomainRepositoryTest extends RdsTestContext {

    @Autowired
    private DrinkDomainRepository sut;

    @Test
    void createDrink() {
        // given
        String categoryKey = "BEER";
        String categoryName = "맥주";
        String drinkName = "스텔라 아르투아";
        String englishName = "STELLA ARTOIS";
        double alcoholByVolume = 5.0;
        String description = "존맛";
        String smallImageFilePath = "small";
        String mediumImageFilePath = "medium";
        String largeImageFilePath = "large";

        CategoryEntity savedCategory = saveCategory(categoryKey, categoryName);
        Category category = Category.create(savedCategory.getId(), savedCategory.getName(), savedCategory.getKey());
        DrinkPortCreateCommand command = DrinkPortCreateCommand.create(drinkName, englishName, alcoholByVolume, description);
        ImageFilePath imageFilePath = ImageFilePath.create(smallImageFilePath, mediumImageFilePath, largeImageFilePath);

        // when
        sut.createDrink(command, category, imageFilePath);

        // then
        DrinkEntity savedDrink = drinkRepository.findAll()
            .stream()
            .findAny()
            .orElseThrow();

        assertThat(savedDrink.getId()).isNotNull();
        assertThat(savedDrink.getCategory().getId()).isEqualTo(savedCategory.getId());
        assertThat(savedDrink.getName()).isEqualTo(drinkName);
        assertThat(savedDrink.getEnglishName()).isEqualTo(englishName);
        assertThat(savedDrink.getPreferenceAvg()).isEqualTo(0.0);
        assertThat(savedDrink.getDescription()).isEqualTo(description);
        assertThat(savedDrink.getAlcoholByVolume()).isEqualTo(alcoholByVolume);
        assertThat(savedDrink.getSmallImageFilePath()).isEqualTo(smallImageFilePath);
        assertThat(savedDrink.getMediumImageFilePath()).isEqualTo(mediumImageFilePath);
        assertThat(savedDrink.getLargeImageFilePath()).isEqualTo(largeImageFilePath);
    }

    private CategoryEntity saveCategory(String categoryKey, String categoryName) {
        return categoryRepository.save(CategoryEntity.builder()
            .key(categoryKey)
            .name(categoryName)
            .build());
    }
}