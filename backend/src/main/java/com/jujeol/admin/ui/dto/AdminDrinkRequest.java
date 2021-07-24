package com.jujeol.admin.ui.dto;

import com.jujeol.drink.application.dto.DrinkRequestDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AdminDrinkRequest {

    private String name;
    private String englishName;
    private Double alcoholByVolume;
    private String imageUrl;
    private AdminCategory category;

    public DrinkRequestDto toDto() {
        return DrinkRequestDto.create(name, englishName, alcoholByVolume, imageUrl, category.getName());
    }
}
