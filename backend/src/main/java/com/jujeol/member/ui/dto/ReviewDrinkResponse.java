package com.jujeol.member.ui.dto;

import com.jujeol.drink.application.dto.DrinkDto;
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
        return new ReviewDrinkResponse(
                drinkDto.getId(),
                drinkDto.getName(),
                drinkDto.getImageUrl()
        );
    }
}
