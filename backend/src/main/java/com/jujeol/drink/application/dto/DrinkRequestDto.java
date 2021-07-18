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
    private String category;

    public static DrinkRequestDto of(String name, String englishName, Double alcoholByVolume,
            String imageUrl, String category) {
        return new DrinkRequestDto(name, englishName, alcoholByVolume, imageUrl, category);
    }

    public Drink toEntity() {
        return Drink.from(name, englishName, alcoholByVolume, imageUrl, Category.matches(category));
    }
}
