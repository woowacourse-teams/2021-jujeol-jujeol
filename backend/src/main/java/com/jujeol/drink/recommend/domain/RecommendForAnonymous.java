package com.jujeol.drink.recommend.domain;

import com.jujeol.drink.drink.domain.repository.DrinkRepository;
import com.jujeol.drink.recommend.application.RecommendStrategy;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;

@RequiredArgsConstructor
public class RecommendForAnonymous implements RecommendStrategy {

    private final DrinkRepository drinkRepository;

    @Override
    public List<RecommendedDrinkResponse> recommend(String category, Long memberId, int pageSize) {
        if(category == null) {
            return drinkRepository.findDrinks(PageRequest.ofSize(pageSize))
                    .stream()
                    .map(drink -> new RecommendedDrinkResponse(drink, 0))
                    .collect(Collectors.toList());
        }
        return drinkRepository.findAllByCategorySorted(category, PageRequest.ofSize(pageSize))
                .stream()
                .map(drink -> new RecommendedDrinkResponse(drink, 0))
                .collect(Collectors.toList());
    }
}
