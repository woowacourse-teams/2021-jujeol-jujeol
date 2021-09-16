package com.jujeol.drink.recommend.infrastructure;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class RecommendationResponse {

    private Long itemId;
    private double expectedPreference;
}
