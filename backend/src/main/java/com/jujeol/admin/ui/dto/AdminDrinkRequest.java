package com.jujeol.admin.ui.dto;

import com.jujeol.drink.drink.application.dto.DrinkRequestDto;
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
    private String categoryKey;

    public DrinkRequestDto toDto() {
        // todo 관리자 image Url 변경
        return DrinkRequestDto.create(name, englishName, alcoholByVolume, imageUrl, imageUrl, imageUrl, categoryKey);
    }
}
