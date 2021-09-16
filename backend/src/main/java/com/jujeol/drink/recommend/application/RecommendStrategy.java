package com.jujeol.drink.recommend.application;

import com.jujeol.drink.drink.domain.Drink;
import java.util.List;

public interface RecommendStrategy {

    List<Drink> recommend(String category, Long memberId, int pageSize);
}
