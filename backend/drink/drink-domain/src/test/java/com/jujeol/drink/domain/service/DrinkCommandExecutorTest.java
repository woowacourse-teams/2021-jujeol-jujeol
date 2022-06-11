package com.jujeol.drink.domain.service;

import com.jujeol.drink.DomainTestContext;
import com.jujeol.drink.domain.exception.NotFoundCategoryException;
import com.jujeol.drink.domain.model.*;
import com.jujeol.drink.domain.usecase.DrinkRegisterUseCase;
import com.jujeol.drink.domain.usecase.command.DrinkRegisterCommand;
import com.jujeol.drink.rds.entity.CategoryEntity;
import com.jujeol.drink.rds.entity.DrinkEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class DrinkCommandExecutorTest extends DomainTestContext {

    @Autowired
    private DrinkCommandExecutor drinkCommandExecutor;

    @Nested
    @DisplayName("주류 등록")
    class DrinkRegisterUseCaseTest {

        private DrinkRegisterUseCase sut;

        @BeforeEach
        void setUp() {
            sut = drinkCommandExecutor;
        }

        @Test
        void 성공() {
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

            DrinkRegisterCommand command = DrinkRegisterCommand.builder()
                .categoryKey(categoryKey)
                .name(DrinkName.from(drinkName))
                .englishName(DrinkEnglishName.from(englishName))
                .alcoholByVolume(AlcoholByVolume.from(alcoholByVolume))
                .imageFilePath(ImageFilePath.create(smallImageFilePath, mediumImageFilePath, largeImageFilePath))
                .description(Description.from(description))
                .build();

            // when
            sut.register(command);

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

        @Test
        void 실패_카테고리_존재x() {
            // given
            String categoryKey = "BEER";
            String drinkName = "스텔라 아르투아";
            String englishName = "STELLA ARTOIS";
            double alcoholByVolume = 5.0;
            String description = "존맛";
            String smallImageFilePath = "small";
            String mediumImageFilePath = "medium";
            String largeImageFilePath = "large";

            DrinkRegisterCommand command = DrinkRegisterCommand.builder()
                .categoryKey(categoryKey)
                .name(DrinkName.from(drinkName))
                .englishName(DrinkEnglishName.from(englishName))
                .alcoholByVolume(AlcoholByVolume.from(alcoholByVolume))
                .imageFilePath(ImageFilePath.create(smallImageFilePath, mediumImageFilePath, largeImageFilePath))
                .description(Description.from(description))
                .build();

            // when, then
            assertThatThrownBy(() -> sut.register(command))
                .isInstanceOf(NotFoundCategoryException.class);
        }
    }

    private CategoryEntity saveCategory(String categoryKey, String categoryName) {
        return categoryRepository.save(CategoryEntity.builder()
            .key(categoryKey)
            .name(categoryName)
            .build());
    }
}