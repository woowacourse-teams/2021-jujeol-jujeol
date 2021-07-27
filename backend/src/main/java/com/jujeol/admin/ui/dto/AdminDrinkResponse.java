package com.jujeol.admin.ui.dto;

import com.jujeol.drink.application.dto.DrinkDto;
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
    private AdminCategory category;
    private double preferenceRate;

    public static AdminDrinkResponse from(DrinkDto drinkDto) {
        return new AdminDrinkResponse(
                drinkDto.getId(),
                drinkDto.getName(),
                drinkDto.getEnglishName(),
                drinkDto.getAlcoholByVolume(),
                drinkDto.getImageUrl(),
                new AdminCategory(drinkDto.getCategoryId(), drinkDto.getCategory()),
                drinkDto.getPreferenceRate()
        );
    }
}
