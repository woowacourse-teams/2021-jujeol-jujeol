package com.jujeol.drink.controller.response;

import com.jujeol.drink.domain.model.Drink;
import lombok.Builder;
import lombok.Getter;

@Getter
public class DrinkDetailResponse {

    private final Long id;
    private final String name;
    private final String englishName;
    private final Double alcoholByVolume;
    private final ImageResponse imageResponse;
    private final CategoryResponse category;
    private final String description;
    private final double preferenceRate;
    private final double preferenceAvg;
    private final double expectedPreference;

    @Builder
    private DrinkDetailResponse(Long id, String name, String englishName, Double alcoholByVolume, ImageResponse imageResponse, CategoryResponse category, String description, double preferenceRate, double preferenceAvg, double expectedPreference) {
        this.id = id;
        this.name = name;
        this.englishName = englishName;
        this.alcoholByVolume = alcoholByVolume;
        this.imageResponse = imageResponse;
        this.category = category;
        this.description = description;
        this.preferenceRate = preferenceRate;
        this.preferenceAvg = preferenceAvg;
        this.expectedPreference = expectedPreference;
    }

    @Getter
    public static class ImageResponse {

        private final String small;
        private final String medium;
        private final String large;

        private ImageResponse(String small, String medium, String large) {
            this.small = small;
            this.medium = medium;
            this.large = large;
        }

        public static ImageResponse create(String small, String medium, String large) {
            return new ImageResponse(small, medium, large);
        }
    }

    @Getter
    public static class CategoryResponse {
        private final Long id;
        private final String name;
        private final String key;

        private CategoryResponse(Long id, String name, String key) {
            this.id = id;
            this.name = name;
            this.key = key;
        }

        public static CategoryResponse create(Long id, String name, String key) {
            return new CategoryResponse(id, name, key);
        }
    }

    public static DrinkDetailResponse from(Drink drink, double expectedPreference) {
        return DrinkDetailResponse.builder()
            .id(drink.getDrinkId())
            .name(drink.getName().value())
            .englishName(drink.getEnglishName().value())
            .alcoholByVolume(drink.getAlcoholByVolume().value())
            .imageResponse(DrinkDetailResponse.ImageResponse.create(
                drink.getImageFilePath().getSmallImageFilePath(),
                drink.getImageFilePath().getMediumImageFilePath(),
                drink.getImageFilePath().getLargeImageFilePath()
            ))
            .category(DrinkDetailResponse.CategoryResponse.create(
                drink.getCategory().getId(),
                drink.getCategory().getName(),
                drink.getCategory().getKey()
            ))
            .description(drink.getDescription().value())
            .preferenceAvg(drink.getPreferenceAvg() == null ? 0 : drink.getPreferenceAvg())
            .expectedPreference(expectedPreference)
            .build();
    }
}
