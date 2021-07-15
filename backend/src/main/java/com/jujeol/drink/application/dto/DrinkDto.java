package com.jujeol.drink.application.dto;

import com.jujeol.drink.domain.Drink;
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
    private String category;

    public static DrinkDto from(Drink drink, String fileServerUrl) {
        return new DrinkDto(drink.getId(),
                drink.getName(),
                drink.getEnglishName(),
                drink.getAlcoholByVolume(),
                fileServerUrl + "/" + drink.getImageFilePath(),
                drink.getCategory()
        );
    }
}