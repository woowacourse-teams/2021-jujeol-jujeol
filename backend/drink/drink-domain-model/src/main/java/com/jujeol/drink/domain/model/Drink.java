package com.jujeol.drink.domain.model;

import lombok.Builder;
import lombok.Getter;

@Getter
public class Drink {

    private final Long drinkId;
    private final DrinkName name;
    private final DrinkEnglishName englishName;
    private final AlcoholByVolume alcoholByVolume;
    private final Description description;
    private final Category category;
    private final ImageFilePath imageFilePath;
    private final Double preferenceAvg;

    @Builder
    public Drink(Long drinkId, DrinkName name, DrinkEnglishName englishName, AlcoholByVolume alcoholByVolume, Description description, Category category, ImageFilePath imageFilePath, Double preferenceAvg) {
        this.drinkId = drinkId;
        this.name = name;
        this.englishName = englishName;
        this.alcoholByVolume = alcoholByVolume;
        this.description = description;
        this.category = category;
        this.imageFilePath = imageFilePath;
        this.preferenceAvg = preferenceAvg;
    }
}
