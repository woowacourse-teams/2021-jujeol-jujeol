package com.jujeol.drink.domain.usecase.command;

import lombok.Getter;

@Getter
public class DrinkPortCreateCommand {

    private String name;
    private String englishName;
    private Double alcoholByVolume;
    private String description;

    private DrinkPortCreateCommand(String name, String englishName, Double alcoholByVolume, String description) {
        this.name = name;
        this.englishName = englishName;
        this.alcoholByVolume = alcoholByVolume;
        this.description = description;
    }

    public static DrinkPortCreateCommand create(String name, String englishName, Double alcoholByVolume, String description) {
        return new DrinkPortCreateCommand(name, englishName, alcoholByVolume, description);
    }
}
