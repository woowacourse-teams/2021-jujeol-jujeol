package com.jujeol.drink.drink.ui.dto;

import com.jujeol.drink.category.ui.dto.CategoryResponse;
import com.jujeol.drink.drink.application.dto.DrinkDto;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@EqualsAndHashCode(of = "id")
public class DrinkResponse {

    private Long id;
    private String name;
    private String englishName;
    private Double alcoholByVolume;
    private ImageResponse imageResponse;
    private CategoryResponse category;
    private double preferenceRate;
    private double preferenceAvg;

    public static DrinkResponse from(DrinkDto drinkDto) {
        return new DrinkResponse(
                drinkDto.getId(),
                drinkDto.getName(),
                drinkDto.getEnglishName(),
                drinkDto.getAlcoholByVolume(),
                ImageResponse.create(drinkDto.getSmallImageFilePath(),
                        drinkDto.getMediumImageFilePath(), drinkDto.getLargeImageFilePath()),
                CategoryResponse.create(drinkDto.getCategoryDto()),
                drinkDto.getPreferenceRate(),
                drinkDto.getPreferenceAvg()
        );
    }
}
