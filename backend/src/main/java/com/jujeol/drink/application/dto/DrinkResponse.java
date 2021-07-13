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
public class DrinkResponse {

    private Long id;
    private String name;
    private Double alcoholByVolume;
    private String imageUrl;
    private String category;

    public static DrinkResponse from(Drink drink, String fileServerUrl) {
        return new DrinkResponse(drink.getId(),
                drink.getName(),
                drink.getAlcoholByVolume(),
                fileServerUrl + "/" + drink.getImageFilePath(),
                drink.getCategory()
            );
    }
}
