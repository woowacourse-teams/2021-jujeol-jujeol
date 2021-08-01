package com.jujeol.drink.domain;

import com.jujeol.drink.domain.repository.DrinkRepository;
import com.jujeol.drink.infrastructure.recommend.RecommendationSystem;
import java.util.List;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class RecommendMember implements RecommendState {

    private final RecommendState recommendState;
    private final RecommendationSystem recommendationSystem;
    private final DrinkRepository drinkRepository;

    @Override
    public List<Drink> recommend(Long memberId, int pageSize) {
        final List<Long> itemIds = recommendationSystem.recommend(memberId, pageSize);

        final List<Drink> drinks = drinkRepository.findAllById(itemIds);
        int remainCount = pageSize - itemIds.size();

        if (remainCount > 0) {
            List<Drink> remainDrinks = recommendState.recommend(memberId, pageSize);
            remainDrinks.removeAll(drinks);
            drinks.addAll(remainDrinks.subList(0, remainCount));
        }

        return drinks;
    }
}
