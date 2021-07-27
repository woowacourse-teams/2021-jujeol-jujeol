package com.jujeol.drink.application.dto;

import com.jujeol.drink.domain.Drink;
import com.jujeol.member.domain.Preference;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@EqualsAndHashCode(of = "id")
public class DrinkDto {

    private Long id;
    private String name;
    private String englishName;
    private Double alcoholByVolume;
    private String imageUrl;
    private CategoryDto categoryDto;
    private double preferenceRate;
    private Long viewCount;
    private double preferenceAvg;

    public static DrinkDto create(
            Drink drink,
            Preference preference,
            String fileServerUrl
    ) {
        return new DrinkDto(drink.getId(),
                drink.getName(),
                drink.getEnglishName(),
                drink.getAlcoholByVolume(),
                fileServerUrl + "/" + drink.getImageFilePath(),
                CategoryDto.create(drink.getCategory()),
                preference.getRate(),
                drink.getViewCount().getViewCount(),
                drink.getPreferenceAvg()
        );
    }
}