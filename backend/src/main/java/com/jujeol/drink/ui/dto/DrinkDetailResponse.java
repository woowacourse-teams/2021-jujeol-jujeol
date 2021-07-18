package com.jujeol.drink.ui.dto;

import com.jujeol.drink.application.dto.DrinkDto;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@EqualsAndHashCode(of = "id")
public class DrinkDetailResponse {

    private Long id;
    private String name;
    private String englishName;
    private Double alcoholByVolume;
    private String imageUrl;
    private CategoryResponse category;
    private double preferenceRate;

    public static DrinkDetailResponse from(DrinkDto drinkDto) {
        return new DrinkDetailResponse(
                drinkDto.getId(),
                drinkDto.getName(),
                drinkDto.getEnglishName(),
                drinkDto.getAlcoholByVolume(),
                drinkDto.getImageUrl(),
                new CategoryResponse(1L, drinkDto.getCategory()),
                drinkDto.getPreferenceRate()
        );
    }
}