package com.jujeol.elasticsearch;

import com.jujeol.drink.drink.domain.Drink;
import com.jujeol.drink.drink.domain.repository.DrinkRepository;
import com.jujeol.elasticsearch.application.SearchHelper;
import com.jujeol.elasticsearch.infrastructure.DrinkSearchResult;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

@Profile("test")
@Component
public class TestInMemorySearchHelper implements SearchHelper {

    private final DrinkRepository drinkRepository;

    public TestInMemorySearchHelper(DrinkRepository drinkRepository) {
        this.drinkRepository = drinkRepository;
    }

    @Override
    public DrinkSearchResult searchDrinkIds(String keyword, Pageable pageable) {
        Page<Drink> drinks = drinkRepository.findWithKeyword(keyword, pageable);
        return new DrinkSearchResult(drinks.getContent(), drinks.getPageable(), drinks.getTotalElements());
    }
}
