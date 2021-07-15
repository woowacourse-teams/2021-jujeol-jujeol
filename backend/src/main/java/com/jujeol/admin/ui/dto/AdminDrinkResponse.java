package com.jujeol.admin.ui.dto;

import com.jujeol.drink.application.dto.DrinkResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class AdminDrinkResponse {

    private Long id;
    private String name;
    private String englishName;
    private String imageUrl;
    private CategoryResponse category;
    private Double alcoholByVolume;

}
