package com.jujeol.drink.application.dto;

import com.jujeol.drink.domain.Drink;
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

    public static DrinkSimpleResponse from(Drink drink, String fileServerUrl) {
        return new DrinkSimpleResponse(drink.getId(),
                drink.getName(),
                drink.getAlcoholByVolume(),
                fileServerUrl + "/" + drink.getImageFilePath()
            );
    }
}
