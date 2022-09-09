package com.jujeol.drink.presenter;

import com.jujeol.IntegrationTestContext;
import com.jujeol.drink.controller.requeset.AdminDrinkSaveRequest;
import com.jujeol.drink.controller.response.AdminDrinkResponse;
import com.jujeol.drink.rds.entity.CategoryEntity;
import com.jujeol.drink.rds.entity.DrinkEntity;
import com.jujeol.exception.ExceptionCodeAndDetails;
import com.jujeol.exception.JujeolBadRequestException;
import com.jujeol.member.resolver.LoginMember;
import java.io.File;
import java.nio.file.Files;
import org.assertj.core.data.Index;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.mock.web.MockMultipartFile;

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
                .hasMessage(ExceptionCodeAndDetails.NOT_EXIST_CATEGORY.getMessage());
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

    @Nested
    @DisplayName("주류 조회")
    class ShowAdminDrinks {

        @Test
        void 조회_성공() {
            // given
            CategoryEntity savedCategory = categoryRepository.save(
                CategoryEntity.builder()
                    .key("key")
                    .name("name")
                    .build()
            );

            DrinkEntity drinkEntity1 = DrinkEntity.builder()
                .name("name")
                .description("description")
                .englishName("englishName")
                .alcoholByVolume(3.5)
                .category(savedCategory)
                .preferenceAvg(2.0)
                .largeImageFilePath("largeImageFile")
                .mediumImageFilePath("mediumImageFile")
                .smallImageFilePath("smallImageFile")
                .build();

            DrinkEntity drinkEntity2 = DrinkEntity.builder()
                .name("name2")
                .description("description2")
                .englishName("englishName2")
                .alcoholByVolume(3.5)
                .category(savedCategory)
                .preferenceAvg(3.0)
                .largeImageFilePath("largeImageFile")
                .mediumImageFilePath("mediumImageFile")
                .smallImageFilePath("smallImageFile")
                .build();
            drinkRepository.save(drinkEntity1);
            drinkRepository.save(drinkEntity2);

            // when
            Page<AdminDrinkResponse> response = sut.showAdminDrinks(Pageable.ofSize(2), LoginMember.anonymous());

            // then
            assertThat(response.getTotalElements()).isEqualTo(2);
            assertThat(response.getContent())
                .satisfies(adminDrinkResponse -> {
                        assertThat(adminDrinkResponse.getName()).isEqualTo("name");
                        assertThat(adminDrinkResponse.getEnglishName()).isEqualTo("englishName");
                    },
                    Index.atIndex(0))
                .satisfies(adminDrinkResponse -> {
                        assertThat(adminDrinkResponse.getName()).isEqualTo("name2");
                        assertThat(adminDrinkResponse.getEnglishName()).isEqualTo("englishName2");
                    },
                    Index.atIndex(1));
        }
    }
}