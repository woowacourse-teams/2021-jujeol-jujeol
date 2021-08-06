package com.jujeol.drink.application;

import com.jujeol.drink.application.dto.DrinkDto;
import com.jujeol.drink.application.dto.DrinkRequestDto;
import com.jujeol.drink.application.dto.SearchDto;
import com.jujeol.drink.domain.Category;
import com.jujeol.drink.domain.Drink;
import com.jujeol.drink.domain.SearchWords;
import com.jujeol.drink.domain.repository.CategoryRepository;
import com.jujeol.drink.domain.repository.DrinkRepository;
import com.jujeol.drink.exception.NotFoundCategoryException;
import com.jujeol.drink.exception.NotFoundDrinkException;
import com.jujeol.member.domain.Preference;
import com.jujeol.member.domain.PreferenceRepository;
import com.jujeol.member.ui.LoginMember;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DrinkService {

    @Value("${file-server.url:}")
    private String fileServerUrl;

    private final DrinkRepository drinkRepository;
    private final PreferenceRepository preferenceRepository;
    private final CategoryRepository categoryRepository;

    public Page<DrinkDto> showDrinksBySearch(SearchDto searchDto, Pageable pageable) {
        SearchWords searchWords = SearchWords.create(searchDto.getSearch());

        List<Drink> drinksBySearch = new ArrayList<>();

        if (searchWords.hasSearchWords()) {
            drinksBySearch.addAll(drinkRepository.findBySearch(searchWords));
        }
        drinksBySearch
                .addAll(drinkRepository.findByCategory(searchWords, searchDto.getCategoryKey()));

        List<DrinkDto> drinkDtos = drinksBySearch.stream()
                .distinct()
                .map(drink -> DrinkDto.create(
                        drink, Preference.create(drink, 0), fileServerUrl))
                .collect(Collectors.toList());

        return new PageImpl<>(drinkDtos, pageable, drinksBySearch.size());
    }

    public Page<DrinkDto> showAllDrinksByPage(Pageable pageable) {
        List<DrinkDto> drinkDtos = drinkRepository.findAll(pageable).stream()
                .map(drink -> DrinkDto.create(
                        drink, Preference.create(drink, 0), fileServerUrl))
                .collect(Collectors.toList());

        return new PageImpl<>(drinkDtos, pageable, drinkDtos.size());
    }

    public Page<DrinkDto> showRecommendDrinks(RecommendStrategy recommendStrategy,
            Pageable pageable, LoginMember loginMember) {
        List<Drink> recommendDrinks = recommendStrategy
                .recommend(loginMember.getId(), pageable.getPageSize());

        List<DrinkDto> drinkDtos = recommendDrinks.stream()
                .map(drink -> DrinkDto.create(
                        drink, Preference.create(drink, 0), fileServerUrl))
                .collect(Collectors.toList());

        return new PageImpl<>(drinkDtos, pageable, drinkDtos.size());
    }

    @Transactional
    public DrinkDto showDrinkDetail(Long id) {
        Drink drink = drinkRepository.findByIdWithFetch(id)
                .orElseThrow(NotFoundDrinkException::new);
        Preference preference = Preference.create(drink, 0.0);
        return DrinkDto.create(drink, preference, fileServerUrl);
    }

    @Transactional
    public DrinkDto showDrinkDetail(Long drinkId, Long memberId) {
        Drink drink = drinkRepository.findByIdWithFetch(drinkId)
                .orElseThrow(NotFoundDrinkException::new);
        Preference preference = preferenceRepository
                .findByMemberIdAndDrinkId(memberId, drinkId)
                .orElseGet(() -> Preference.anonymousPreference(drink));
        return DrinkDto.create(drink, preference, fileServerUrl);
    }

    @Transactional
    public void insertDrinks(List<DrinkRequestDto> drinkRequests) {
        final List<Drink> drinks = new ArrayList<>();

        List<Category> categories = categoryRepository.findAll();
        for (DrinkRequestDto drinkRequest : drinkRequests) {
            Category category = findCategory(categories, drinkRequest.getCategoryKey());
            drinks.add(drinkRequest.toEntity(category));
        }
        drinkRepository.batchInsert(drinks);
    }

    private Category findCategory(List<Category> categories, String categoryKey) {
        return categories.stream()
                .filter(category -> category.matchesKey(categoryKey))
                .findAny()
                .orElseThrow(NotFoundCategoryException::new);
    }

    @Transactional
    public void updateDrink(Long id, DrinkRequestDto drinkRequest) {
        final Drink drink = drinkRepository.findById(id).orElseThrow(NotFoundDrinkException::new);

        Category category = categoryRepository.findByKey(drinkRequest.getCategoryKey())
                .orElseThrow(NotFoundCategoryException::new);

        drink.updateInfo(
                drinkRequest.getName(),
                drinkRequest.getEnglishName(),
                drinkRequest.getImageUrl(),
                category,
                drinkRequest.getAlcoholByVolume()
        );
    }

    @Transactional
    public void removeDrink(Long id) {
        drinkRepository.deleteById(id);
    }
}
