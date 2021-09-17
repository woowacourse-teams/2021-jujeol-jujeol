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
    private AdminCategoryResponse category;
    private double preferenceRate;
    private String description;

    public static AdminDrinkResponse from(DrinkDto drinkDto) {
        // todo 관리자 image Url 변경 small medium large
        return new AdminDrinkResponse(
                drinkDto.getId(),
                drinkDto.getName(),
                drinkDto.getEnglishName(),
                drinkDto.getAlcoholByVolume(),
                drinkDto.getSmallImageFilePath(),
                AdminCategoryResponse.create(drinkDto.getCategoryDto()),
                drinkDto.getPreferenceRate(),
                drinkDto.getDescription()
        );
    }
}
