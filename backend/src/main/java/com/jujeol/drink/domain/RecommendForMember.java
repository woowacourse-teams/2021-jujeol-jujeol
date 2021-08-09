package com.jujeol.drink.domain;

import com.jujeol.drink.application.RecommendStrategy;
import com.jujeol.drink.domain.repository.DrinkRepository;
import com.jujeol.drink.infrastructure.recommend.RecommendationSystem;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;

@RequiredArgsConstructor
public class RecommendForMember implements RecommendStrategy {

    private final RecommendationSystem recommendationSystem;
    private final DrinkRepository drinkRepository;

    @Override
    public List<Drink> recommend(Long memberId, int pageSize) {
        final List<Long> itemIds = recommendationSystem.recommend(memberId, pageSize);

        List<Drink> drinks = drinkRepository.findAllById(itemIds);

        if (itemIds.size() < pageSize) {
            return addDrinkByPreferenceAvg(drinks, memberId, pageSize);
        }

        return drinks;
    }

    private List<Drink> addDrinkByPreferenceAvg(List<Drink> drinks, Long memberId, int pageSize) {
        drinks.addAll(drinkRepository.findDrinksForMember(memberId, Pageable.ofSize(pageSize)));
        return drinks.stream().distinct()
                .limit(pageSize)
                .collect(Collectors.toList());
    }
}
