package com.jujeol.member.ui.dto;

import com.jujeol.drink.application.dto.DrinkDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class MemberDrinkResponse {

    private Long id;
    private String name;
    private String imageUrl;
    private double preferenceRate;

    public static MemberDrinkResponse from(DrinkDto drinkDto) {
        return new MemberDrinkResponse(
                drinkDto.getId(),
                drinkDto.getName(),
                drinkDto.getImageUrl(),
                drinkDto.getPreferenceRate()
        );
    }
}
