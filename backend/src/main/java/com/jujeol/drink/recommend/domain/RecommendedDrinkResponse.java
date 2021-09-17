package com.jujeol.drink.recommend.domain;

import com.jujeol.drink.drink.domain.Drink;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "drink")
public class RecommendedDrinkResponse {

    private Drink drink;
    private double expectedPreference;

}
