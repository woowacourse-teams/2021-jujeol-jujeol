package com.jujeol.drink.controller.requeset;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AdminDrinkSaveRequest {

    private String name;
    private String englishName;
    private Double alcoholByVolume;
    private String categoryKey;
    private String description;

    @Builder
    public AdminDrinkSaveRequest(String name, String englishName, Double alcoholByVolume, String categoryKey, String description) {
        this.name = name;
        this.englishName = englishName;
        this.alcoholByVolume = alcoholByVolume;
        this.categoryKey = categoryKey;
        this.description = description;
    }
}
