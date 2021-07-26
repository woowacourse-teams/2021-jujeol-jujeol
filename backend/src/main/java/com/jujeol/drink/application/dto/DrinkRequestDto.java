package com.jujeol.drink.application.dto;

import com.jujeol.drink.domain.Category;
import com.jujeol.drink.domain.Drink;
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
    private String imageUrl;
    private Long categoryId;

    public static DrinkRequestDto of(String name, String englishName, Double alcoholByVolume,
            String imageUrl, Long categoryId) {
        return new DrinkRequestDto(
                name,
                englishName,
                alcoholByVolume,
                imageUrl,
                categoryId
        );
    }

    public Drink toEntity(Category category) {
        // todo preferenceAvg 변경
        return Drink.from(
                name,
                englishName,
                alcoholByVolume,
                imageUrl,
                3.8,
                category
        );
    }
}
