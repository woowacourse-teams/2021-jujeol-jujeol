package com.jujeol.drink.drink.domain.repository;

import com.jujeol.drink.drink.domain.Drink;
import com.jujeol.drink.drink.domain.SearchWords;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface DrinkCustomRepository {

    Optional<Drink> findByIdWithFetch(Long drinkId);

    Optional<Drink> findByName(String drinkName);

    List<Drink> findDrinks(Pageable pageable);

    List<Drink> findDrinksForMember(Long memberId, Pageable pageable, String category);

    List<Drink> findDrinksForMember(Long memberId, Pageable pageable);

    Page<Drink> findAllByCategorySorted(String category, Pageable pageable);

    Page<Drink> findAllByCategory(String category, Pageable pageable);

    Page<Drink> findAllSortByPreference(Pageable pageable);

    List<Drink> findBySearch(SearchWords searchWords, Pageable pageable);
}
