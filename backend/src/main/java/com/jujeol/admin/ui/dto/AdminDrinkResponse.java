package com.jujeol.admin.ui.dto;

import com.jujeol.drink.drink.application.dto.DrinkDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AdminDrinkResponse {

    private Long id;
    private String name;
    private String englishName;
    private Double alcoholByVolume;
    private String imageUrl;
    private String description;
    private AdminCategoryResponse category;
    private double preferenceRate;
    private String description;

    public static AdminDrinkResponse from(DrinkDto drinkDto) {
        return new AdminDrinkResponse(
                drinkDto.getId(),
                drinkDto.getName(),
                drinkDto.getEnglishName(),
                drinkDto.getAlcoholByVolume(),
                drinkDto.getImageUrl(),
                "엄청나게 긴 설명이라능 엄청나게 긴 설명이라능엄청나게 긴 설명이라능엄청나게 긴 설명이라능엄청나게 긴 설명이라능엄청나게 긴 설명이라능엄청나게 긴 설명이라능엄청나게 긴 설명이라능엄청나게 긴 설명이라능엄청나게 긴 설명이라능엄청나게 긴 설명이라능엄청나게 긴 설명이라능엄청나게 긴 설명이라능엄청나게 긴 설명이라능엄청나게 긴 설명이라능엄청나게 긴 설명이라능엄청나게 긴 설명이라능엄청나게 긴 설명이라능엄청나게 긴 설명이라능",
                AdminCategoryResponse.create(drinkDto.getCategoryDto()),
                drinkDto.getPreferenceRate(),
                drinkDto.getDescription()
        );
    }
}
