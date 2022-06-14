package com.jujeol.drink.presenter;

import com.jujeol.IntegrationTestContext;
import com.jujeol.commons.BadStatus;
import com.jujeol.commons.exception.JujeolBadRequestException;
import com.jujeol.drink.controller.requeset.AdminDrinkSaveRequest;
import com.jujeol.drink.rds.entity.CategoryEntity;
import com.jujeol.drink.rds.entity.DrinkEntity;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockMultipartFile;

import java.io.File;
import java.nio.file.Files;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class DrinkAdminPresenterTest extends IntegrationTestContext {

    public static final File TEST_IMAGE = new File(new File("").getAbsolutePath() + "/src/test/resources/static/test.png");

    @Autowired
    private DrinkAdminPresenter sut;

    @Nested
    @DisplayName("어드민 주류 등록")
    class SaveDrink {

        @Test
        void 등록_성공() throws Exception {
            // given
            String name = "애플";
            String englishName = "Apple";
            double alcoholByVolume = 8.2;
            String categoryKey = "BEER";
            String description = "사과맛이 나는 맥주이다.";

            CategoryEntity savedCategory = createCategory(categoryKey, "맥주");

            AdminDrinkSaveRequest request = AdminDrinkSaveRequest.builder()
                .name(name)
                .englishName(englishName)
                .alcoholByVolume(alcoholByVolume)
                .categoryKey(categoryKey)
                .description(description)
                .build();

            // when
            sut.saveDrink(request, getMockImage());

            // then
            DrinkEntity savedDrink = drinkRepository.findAll()
                .stream()
                .findAny()
                .orElseThrow();

            assertThat(savedDrink.getId()).isNotNull();
            assertThat(savedDrink.getCategory().getId()).isEqualTo(savedCategory.getId());
            assertThat(savedDrink.getName()).isEqualTo(name);
            assertThat(savedDrink.getEnglishName()).isEqualTo(englishName);
            assertThat(savedDrink.getPreferenceAvg()).isEqualTo(0.0);
            assertThat(savedDrink.getDescription()).isEqualTo(description);
            assertThat(savedDrink.getAlcoholByVolume()).isEqualTo(alcoholByVolume);
            assertThat(savedDrink.getSmallImageFilePath()).isEqualTo("w200/test_w200.png");
            assertThat(savedDrink.getMediumImageFilePath()).isEqualTo("w400/test_w400.png");
            assertThat(savedDrink.getLargeImageFilePath()).isEqualTo("w600/test_w600.png");
        }

        @Test
        void 카테고리_존재x() {
            // given
            String name = "애플";
            String englishName = "Apple";
            double alcoholByVolume = 8.2;
            String categoryKey = "BEER";
            String description = "사과맛이 나는 맥주이다.";

            AdminDrinkSaveRequest request = AdminDrinkSaveRequest.builder()
                .name(name)
                .englishName(englishName)
                .alcoholByVolume(alcoholByVolume)
                .categoryKey(categoryKey)
                .description(description)
                .build();

            // when, then
            assertThatThrownBy(() -> sut.saveDrink(request, getMockImage()))
                .isInstanceOf(JujeolBadRequestException.class)
                .hasMessage(BadStatus.NOT_EXIST_CATEGORY.getMessage());
        }
    }

    private CategoryEntity createCategory(String categoryKey, String categoryName) {
        return categoryRepository.save(
            CategoryEntity.builder()
                .key(categoryKey)
                .name(categoryName)
                .build()
        );
    }

    private MockMultipartFile getMockImage() throws Exception {
        return new MockMultipartFile(
            TEST_IMAGE.getAbsolutePath(),
            TEST_IMAGE.getName(),
            "image/png",
            Files.readAllBytes(TEST_IMAGE.toPath()));
    }
}