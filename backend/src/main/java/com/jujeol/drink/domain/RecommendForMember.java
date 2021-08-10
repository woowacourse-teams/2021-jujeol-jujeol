package com.jujeol.drink.domain;

import com.jujeol.drink.application.RecommendStrategy;
import com.jujeol.drink.domain.repository.DrinkRepository;
import com.jujeol.drink.infrastructure.recommend.RecommendationSystem;
import com.jujeol.member.domain.Preference;
import com.jujeol.member.domain.PreferenceRepository;
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
        final List<Long> itemIds = recommendationSystem.recommend(memberId, pageSize, preferences);
        final List<Long> triedItems = preferences.stream()
                .filter(preference -> preference.getMember().getId().equals(memberId))
                .map(preference -> preference.getDrink().getId())
                .collect(Collectors.toList());

        List<Drink> drinks = drinkRepository.findAllById(itemIds);
        drinks = setFirstNoTriedItem(drinks, triedItems);

        if (itemIds.size() < pageSize) {
            addItemByPreferenceAvg(drinks, triedItems, pageSize);
        }

        return drinks;
    }

    private void addItemByPreferenceAvg(List<Drink> drinks, List<Long> triedItems, int pageSize) {
        List<Drink> drinksByPreference = drinkRepository
                .findDrinks(Pageable.ofSize(pageSize));
        drinksByPreference = setFirstNoTriedItem(drinksByPreference, triedItems);
        for (Drink drink : drinksByPreference) {
            if (drinks.size() < pageSize && !drinks.contains(drink)) {
                drinks.add(drink);
            }
        }
    }

    private List<Drink> setFirstNoTriedItem(List<Drink> drinks, List<Long> triedItems) {
        LinkedList<Drink> triedDrinks = new LinkedList<>();
        for (Drink drink : drinks) {
            addDrinkByCheckingTriedOrNot(triedItems, triedDrinks, drink);
        }
        return triedDrinks;
    }

    private void addDrinkByCheckingTriedOrNot(List<Long> triedItems, LinkedList<Drink> triedDrinks, Drink drink) {
        if(triedItems.contains(drink.getId())) {
            triedDrinks.addLast(drink);
            return;
        }
        triedDrinks.addFirst(drink);
    }
}
