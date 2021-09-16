package com.jujeol.member.member.ui.dto;

import com.jujeol.drink.drink.application.dto.DrinkDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class ReviewDrinkResponse {

    private Long drinkId;
    private String name;
    private String imageUrl;

    public static ReviewDrinkResponse create(
            DrinkDto drinkDto
    ) {
        // todo MediumImage
        return new ReviewDrinkResponse(
                drinkDto.getId(),
                drinkDto.getName(),
                drinkDto.getMediumImageFilePath()
        );
    }
}
