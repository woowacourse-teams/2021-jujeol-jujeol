package com.jujeol.drink.controller.response;

import lombok.Builder;
import lombok.Getter;

@Getter
public class DrinkSearchResponse {

    private Long id;
    private String name;
    private String englishName;
    private Double alcoholByVolume;
    private ImageResponse imageResponse;
    private CategoryResponse category;
    private String description;
    private double preferenceRate;
    private double preferenceAvg;
    private double expectedPreference;

    @Builder
    public DrinkSearchResponse(Long id, String name, String englishName, Double alcoholByVolume, ImageResponse imageResponse, CategoryResponse category, String description, double preferenceRate, double preferenceAvg, double expectedPreference) {
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

        private String small;
        private String medium;
        private String large;

        @Builder
        public ImageResponse(String small, String medium, String large) {
            this.small = small;
            this.medium = medium;
            this.large = large;
        }
    }

    @Getter
    public static class CategoryResponse {

        private Long id;
        private String name;
        private String key;

        @Builder
        public CategoryResponse(Long id, String name, String key) {
            this.id = id;
            this.name = name;
            this.key = key;
        }
    }
}
