package com.jujeol.drink.domain.repository;

import com.jujeol.drink.domain.Drink;
import com.jujeol.drink.domain.RecommendationTheme;
import com.jujeol.drink.domain.Search;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface DrinkCustomRepository {

    void batchInsert(List<Drink> drinks);

    Page<Drink> findBySearch(Search search, Pageable pageable);

    Page<Drink> findByRecommendation(RecommendationTheme recommendationTheme, Pageable pageable);
}
