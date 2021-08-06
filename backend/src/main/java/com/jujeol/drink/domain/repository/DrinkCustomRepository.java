package com.jujeol.drink.domain.repository;

import com.jujeol.drink.domain.Drink;
import com.jujeol.drink.domain.RecommendationTheme;
import com.jujeol.drink.domain.SearchWords;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface DrinkCustomRepository {

    void batchInsert(List<Drink> drinks);

    List<Drink> findBySearch(SearchWords searchWords);

    List<Drink> findByCategory(SearchWords searchWords, String categoryKey);

    Page<Drink> findByRecommendation(RecommendationTheme recommendationTheme, Pageable pageable);
}
