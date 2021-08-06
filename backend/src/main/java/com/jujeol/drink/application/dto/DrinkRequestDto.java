package com.jujeol.drink.application.dto;

import com.jujeol.drink.domain.Category;
import com.jujeol.drink.domain.Drink;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class DrinkRequestDto {

    private String name;
    private String englishName;
    private Double alcoholByVolume;
    private String imageUrl;
    private String categoryKey;

    public static DrinkRequestDto create(
            String name, String englishName, Double alcoholByVolume,
            String imageUrl, String categoryKey
    ) {
        return new DrinkRequestDto(
                name,
                englishName,
                alcoholByVolume,
                imageUrl,
                categoryKey
        );
    }

    public Drink toEntity(Category category) {
        return Drink.create(
                name,
                englishName,
                alcoholByVolume,
                imageUrl,
                0.0,
                category
        );
    }
}
