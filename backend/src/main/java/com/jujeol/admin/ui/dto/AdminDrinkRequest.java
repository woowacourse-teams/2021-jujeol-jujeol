package com.jujeol.admin.ui.dto;

import com.jujeol.drink.application.dto.AdminDrinkRequestDto;
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
    private Long categoryId;

    public AdminDrinkRequestDto toDto() {
        return AdminDrinkRequestDto.create(name, englishName, alcoholByVolume, imageUrl, categoryId);
    }
}
