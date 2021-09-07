package com.jujeol.drink.drink.application.dto;

import com.jujeol.drink.category.domain.Category;
import com.jujeol.drink.drink.domain.Drink;
import java.util.List;
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
    private String smallImageUrl;
    private String mediumImageUrl;
    private String largeImageUrl;
    private String categoryKey;

    public static DrinkRequestDto create(
            String name, String englishName, Double alcoholByVolume,
            String smallImageUrl, String mediumImageUrl, String largeImageUrl, String categoryKey
    ) {
        return new DrinkRequestDto(
                name,
                englishName,
                alcoholByVolume,
                smallImageUrl,
                mediumImageUrl,
                largeImageUrl,
                categoryKey
        );
    }

    public Drink toEntity(Category category) {
        return Drink.create(
                name,
                englishName,
                alcoholByVolume,
                List.of(smallImageUrl, mediumImageUrl, largeImageUrl),
                0.0,
                category
        );
    }
}
