package com.jujeol.drink.recommend.application;

import com.jujeol.drink.recommend.domain.RecommendedDrinkResponse;
import java.util.List;

public interface RecommendStrategy {

    List<RecommendedDrinkResponse> recommend(String category, Long memberId, int pageSize);
}
