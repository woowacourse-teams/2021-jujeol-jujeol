package com.jujeol.drink.domain;

import com.jujeol.drink.application.RecommendStrategy;
import com.jujeol.drink.domain.repository.DrinkRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;

@RequiredArgsConstructor
public class RecommendForAnonymous implements RecommendStrategy {

    private final DrinkRepository drinkRepository;

    @Override
    public List<Drink> recommend(Long memberId, int pageSize) {
        return drinkRepository.findDrinks(PageRequest.ofSize(pageSize));
    }
}
