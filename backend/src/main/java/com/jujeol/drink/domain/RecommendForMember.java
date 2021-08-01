package com.jujeol.drink.domain;

import com.jujeol.drink.application.RecommendStrategy;
import com.jujeol.drink.domain.repository.DrinkRepository;
import com.jujeol.drink.infrastructure.recommend.RecommendationSystem;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class RecommendForMember implements RecommendStrategy {

    private final RecommendStrategy recommendStrategy;
    private final RecommendationSystem recommendationSystem;
    private final DrinkRepository drinkRepository;

    @Override
    public List<Drink> recommend(Long memberId, int pageSize) {
        final List<Long> itemIds = recommendationSystem.recommend(memberId, pageSize);

        List<Drink> drinks = drinkRepository.findAllById(itemIds);

        if(drinks.size() == 0) {
            drinks = new ArrayList<>();
        }

        int remainCount = pageSize - itemIds.size();

        if (remainCount > 0) {
            List<Drink> remainDrinks = recommendStrategy.recommend(memberId, pageSize);
            remainDrinks.removeAll(drinks);
            drinks.addAll(remainDrinks.subList(0, remainCount));
        }

        return drinks;
    }
}
