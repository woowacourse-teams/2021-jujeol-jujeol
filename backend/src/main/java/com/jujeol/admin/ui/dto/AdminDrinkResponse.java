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

    public void changeInfo(DrinkRequest drinkRequest) {
        this.name = drinkRequest.getName();
        this.englishName = drinkRequest.getEnglishName();
        this.imageUrl = drinkRequest.getImageUrl();
        this.category.changeCategory(drinkRequest.getCategory());
        this.alcoholByVolume = drinkRequest.getAlcoholByVolume();
    }
}
