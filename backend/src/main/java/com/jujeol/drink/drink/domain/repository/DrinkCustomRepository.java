package com.jujeol.drink.drink.domain.repository;

import com.jujeol.drink.drink.domain.Drink;
import com.jujeol.drink.drink.domain.SearchWords;
import com.jujeol.drink.recommend.domain.RecommendationTheme;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface DrinkCustomRepository {

    void batchInsert(List<Drink> drinks);

    List<Drink> findByIds(List<Long> iDs);

    List<Drink> findBySearch(SearchWords searchWords, List<String> categoryNames);

    List<Drink> findByCategory(SearchWords searchWords, String categoryKey);

    Page<Drink> findByRecommendation(RecommendationTheme recommendationTheme, Pageable pageable);
}
