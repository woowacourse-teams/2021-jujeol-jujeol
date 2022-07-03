package com.jujeol.drink.controller.response;

import lombok.Builder;
import lombok.Getter;

@Getter
public class AdminDrinkResponse {

    private final Long id;
    private final String name;
    private final String englishName;
    private final Double alcoholByVolume;
    private final ImageUrlResponse imageUrl;
    private final CategoryResponse category;
    private final String description;

    @Builder
    public AdminDrinkResponse(Long id, String name, String englishName, Double alcoholByVolume, ImageUrlResponse imageUrl, CategoryResponse category, String description) {
        this.id = id;
        this.name = name;
        this.englishName = englishName;
        this.alcoholByVolume = alcoholByVolume;
        this.imageUrl = imageUrl;
        this.category = category;
        this.description = description;
    }

    @Getter
    public static class ImageUrlResponse {

        private final String smallImageFilePath;
        private final String mediumImageFilePath;
        private final String largeImageFilePath;

        private ImageUrlResponse(String smallImageFilePath, String mediumImageFilePath, String largeImageFilePath) {
            this.smallImageFilePath = smallImageFilePath;
            this.mediumImageFilePath = mediumImageFilePath;
            this.largeImageFilePath = largeImageFilePath;
        }

        public static ImageUrlResponse create(
            String smallImageFilePath,
            String mediumImageFilePath,
            String largeImageFilePath
        ) {
            return new ImageUrlResponse(smallImageFilePath, mediumImageFilePath, largeImageFilePath);
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
}
