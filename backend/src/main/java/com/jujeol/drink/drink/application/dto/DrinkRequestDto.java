package com.jujeol.drink.drink.application.dto;

import com.jujeol.drink.category.domain.Category;
import com.jujeol.drink.drink.domain.Drink;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class DrinkRequestDto {

    private String name;
    private String englishName;
    private Double alcoholByVolume;
    private ImageFilePathDto imageFilePathDto;
    private String categoryKey;
    private String description;

    public static DrinkRequestDto create(
        String name, String englishName, Double alcoholByVolume,
        String smallImageUrl, String mediumImageUrl, String largeImageUrl, String categoryKey,
        String description
    ) {
        return new DrinkRequestDto(
            name,
            englishName,
            alcoholByVolume,
            ImageFilePathDto.create(smallImageUrl, mediumImageUrl, largeImageUrl),
            categoryKey,
            description
        );
    }

    public Drink toEntity(Category category) {
        return Drink.create(
            name,
            englishName,
            alcoholByVolume,
            imageFilePathDto.toEntity(),
            0.0,
            category,
            description
        );
    }
}
