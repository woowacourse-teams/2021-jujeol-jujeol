package com.jujeol.drink.application;

import com.jujeol.drink.domain.Drink;
import java.util.List;

public interface RecommendStrategy {

    List<Drink> recommend(Long memberId, int pageSize);
}
