package com.jujeol.drink.domain.model;

import lombok.Builder;
import lombok.Getter;

@Getter
public class Drink {

    private Long drinkId;
    private DrinkName name;
    private DrinkEnglishName englishName;
    private AlcoholByVolume alcoholByVolume;
    private Description description;
    private Category category;
    private ImageFilePath imageFilePath;

    @Builder
    public Drink(Long drinkId, DrinkName name, DrinkEnglishName englishName, AlcoholByVolume alcoholByVolume, Description description, Category category, ImageFilePath imageFilePath) {
        this.drinkId = drinkId;
        this.name = name;
        this.englishName = englishName;
        this.alcoholByVolume = alcoholByVolume;
        this.description = description;
        this.category = category;
        this.imageFilePath = imageFilePath;
    }
}
