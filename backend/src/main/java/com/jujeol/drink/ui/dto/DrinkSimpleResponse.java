package com.jujeol.drink.ui.dto;

import com.jujeol.drink.application.dto.DrinkDto;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@EqualsAndHashCode(of = "id")
public class DrinkSimpleResponse {

    private Long id;
    private String name;
    private Double alcoholByVolume;
    private String imageUrl;

    public static DrinkSimpleResponse from(DrinkDto drinkDto) {
        return new DrinkSimpleResponse(drinkDto.getId(),
                drinkDto.getName(),
                drinkDto.getAlcoholByVolume(),
                drinkDto.getImageUrl()
        );
    }
}
