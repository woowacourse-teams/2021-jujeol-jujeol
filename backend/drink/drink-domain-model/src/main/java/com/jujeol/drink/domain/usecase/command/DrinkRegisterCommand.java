package com.jujeol.drink.domain.usecase.command;

import com.jujeol.drink.domain.model.*;
import lombok.Builder;
import lombok.Getter;

@Getter
public class DrinkRegisterCommand {

    private DrinkName name;
    private DrinkEnglishName englishName;
    private AlcoholByVolume alcoholByVolume;
    private ImageFilePath imageFilePath;
    private String categoryKey;
    private Description description;

    @Builder
    public DrinkRegisterCommand(DrinkName name, DrinkEnglishName englishName, AlcoholByVolume alcoholByVolume, ImageFilePath imageFilePath, String categoryKey, Description description) {
        this.name = name;
        this.englishName = englishName;
        this.alcoholByVolume = alcoholByVolume;
        this.imageFilePath = imageFilePath;
        this.categoryKey = categoryKey;
        this.description = description;
    }
}
