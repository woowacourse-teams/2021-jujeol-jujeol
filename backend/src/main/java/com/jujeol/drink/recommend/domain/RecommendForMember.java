package com.jujeol.drink.recommend.domain;

import com.jujeol.drink.drink.domain.Drink;
import com.jujeol.drink.drink.domain.repository.DrinkRepository;
import com.jujeol.drink.recommend.application.RecommendStrategy;
import com.jujeol.drink.recommend.infrastructure.RecommendationSystem;
import com.jujeol.preference.domain.Preference;
import com.jujeol.preference.domain.PreferenceRepository;
import java.util.LinkedList;
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
    public List<Drink> recommend(Long memberId, int pageSize) {
        List<Preference> preferences = preferenceRepository.findAll();
        final List<Preference> myPreferences = preferences.stream()
                .filter(preference -> preference.getMember().getId().equals(memberId))
                .collect(Collectors.toList());
        final List<Long> itemIds = recommendationSystem.recommend(memberId, pageSize, preferences);

        List<Drink> drinks = drinkRepository.findAllById(itemIds);
        drinks = setFirstNoTriedItem(drinks, myPreferences);

        if (itemIds.size() < pageSize) {
            addItemByPreferenceAvg(drinks, myPreferences, memberId, pageSize);
        }

        return drinks;
    }

    private void addItemByPreferenceAvg(List<Drink> drinks, List<Preference> myPreferences, Long memberId, int pageSize) {
        List<Drink> drinksByPreference = drinkRepository
                .findDrinksForMember(memberId, Pageable.ofSize(pageSize));
        drinksByPreference = setFirstNoTriedItem(drinksByPreference, myPreferences);
        for (Drink drink : drinksByPreference) {
            if (drinks.size() < pageSize && !drinks.contains(drink)) {
                drinks.add(drink);
            }
        }
    }

    private List<Drink> setFirstNoTriedItem(List<Drink> drinks, List<Preference> myPreferences) {
        LinkedList<Drink> triedDrinks = new LinkedList<>();
        for (Drink drink : drinks) {
            addDrinkByCheckingTriedOrNot(myPreferences, triedDrinks, drink);
        }
        return triedDrinks;
    }

    private void addDrinkByCheckingTriedOrNot(List<Preference> myPreferences, LinkedList<Drink> triedDrinks,
            Drink drink) {
        myPreferences.stream()
                .filter(preference -> preference.getDrink().getId().equals(drink.getId()))
                .findAny()
                .ifPresentOrElse(preference -> {
                    if(preference.getRate() > 3) {
                            triedDrinks.addLast(drink);
                    }
        }, () -> triedDrinks.addFirst(drink));
    }
}
