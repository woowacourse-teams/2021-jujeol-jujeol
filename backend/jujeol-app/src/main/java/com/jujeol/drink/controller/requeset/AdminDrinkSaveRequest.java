package com.jujeol.drink.controller.requeset;

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
}
