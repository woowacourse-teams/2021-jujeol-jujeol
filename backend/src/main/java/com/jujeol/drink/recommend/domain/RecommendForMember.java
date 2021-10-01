package com.jujeol.drink.recommend.domain;

import com.jujeol.drink.drink.domain.repository.DrinkRepository;
import com.jujeol.drink.recommend.application.RecommendStrategy;
import com.jujeol.drink.recommend.infrastructure.RecommendationResponse;
import com.jujeol.drink.recommend.infrastructure.RecommendationSystem;
import com.jujeol.preference.domain.Preference;
import com.jujeol.preference.domain.PreferenceRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;

@RequiredArgsConstructor
public class RecommendForMember implements RecommendStrategy {

    private final RecommendationSystem recommendationSystem;
    private final DrinkRepository drinkRepository;
    private final PreferenceRepository preferenceRepository;

    @Override
    public List<RecommendedDrinkResponse> recommend(String category, Long memberId, int pageSize) {
        List<Preference> preferences;
        if (category == null) {
            preferences = preferenceRepository.findAll();
        } else {
            preferences = preferenceRepository.findAllByCategory(category);
        }
        final List<Preference> myPreferences = preferences.stream()
                .filter(preference -> preference.getMember().getId().equals(memberId))
                .collect(Collectors.toList());
        final List<RecommendationResponse> recommendedItems = recommendationSystem
                .recommend(memberId, pageSize, preferences);

        List<RecommendedDrinkResponse> drinks = recommendedItems.stream()
                .map(res -> new RecommendedDrinkResponse(drinkRepository.getById(res.getItemId()),
                        res.getExpectedPreference()))
                .collect(Collectors.toList());

        drinks = sortByNoTriedItem(drinks, myPreferences);

        if (recommendedItems.size() < pageSize) {
            addItemByPreferenceAvg(drinks, myPreferences, memberId, pageSize, category);
        }

        return drinks;
    }

    private void addItemByPreferenceAvg(List<RecommendedDrinkResponse> drinks,
            List<Preference> myPreferences,
            Long memberId, int pageSize, String category) {
        List<RecommendedDrinkResponse> drinksByPreference;
        if (category != null) {
            drinksByPreference = drinkRepository
                    .findDrinksForMember(memberId, Pageable.ofSize(pageSize), category)
                    .stream()
                    .map(drink -> new RecommendedDrinkResponse(drink, 0))
                    .collect(Collectors.toList());
        } else {
            drinksByPreference = drinkRepository
                    .findDrinksForMember(memberId, Pageable.ofSize(pageSize))
                    .stream()
                    .map(drink -> new RecommendedDrinkResponse(drink, 0))
                    .collect(Collectors.toList());
        }

        drinksByPreference = sortByNoTriedItem(drinksByPreference, myPreferences);
        for (RecommendedDrinkResponse drink : drinksByPreference) {
            if (drinks.size() < pageSize && !drinks.contains(drink)) {
                drinks.add(drink);
            }
        }
    }

    private List<RecommendedDrinkResponse> sortByNoTriedItem(List<RecommendedDrinkResponse> drinks,
            List<Preference> myPreferences) {
        List<RecommendedDrinkResponse> triedDrinks = new ArrayList<>();
        List<RecommendedDrinkResponse> noTriedDrinks = new ArrayList<>();
        for (RecommendedDrinkResponse drink : drinks) {
            addDrinkByCheckingTriedOrNot(myPreferences, triedDrinks, noTriedDrinks, drink);
        }
        noTriedDrinks.addAll(triedDrinks);
        return noTriedDrinks;
    }

    private void addDrinkByCheckingTriedOrNot(List<Preference> myPreferences,
            List<RecommendedDrinkResponse> triedDrinks,
            List<RecommendedDrinkResponse> noTriedDrinks,
            RecommendedDrinkResponse drink) {
        myPreferences.stream()
                .filter(preference -> preference.getDrink().getId()
                        .equals(drink.getDrink().getId()))
                .findAny()
                .ifPresentOrElse(preference -> {
                    if (preference.getRate() > 3) {
                        triedDrinks.add(drink);
                    }
                }, () -> noTriedDrinks.add(drink));
    }
}
