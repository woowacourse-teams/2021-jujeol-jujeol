package com.jujeol.drink.ui.dto;

import com.jujeol.drink.application.dto.DrinkDto;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@EqualsAndHashCode(of = "id")
@ToString
public class DrinkDetailResponse {

    private Long id;
    private String name;
    private String englishName;
    private Double alcoholByVolume;
    private String imageUrl;
    private CategoryResponse category;
    private double preferenceRate;
    private double preferenceAvg;

    public static DrinkDetailResponse from(DrinkDto drinkDto) {
        return new DrinkDetailResponse(
                drinkDto.getId(),
                drinkDto.getName(),
                drinkDto.getEnglishName(),
                drinkDto.getAlcoholByVolume(),
                drinkDto.getImageUrl(),
                CategoryResponse.create(drinkDto.getCategoryDto()),
                drinkDto.getPreferenceRate(),
                drinkDto.getPreferenceAvg()
        );
    }
}
