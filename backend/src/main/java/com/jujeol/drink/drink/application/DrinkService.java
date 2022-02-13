package com.jujeol.drink.drink.application;

import com.jujeol.drink.category.domain.Category;
import com.jujeol.drink.category.domain.CategoryRepository;
import com.jujeol.drink.category.exception.NotFoundCategoryException;
import com.jujeol.drink.drink.application.dto.DrinkDto;
import com.jujeol.drink.drink.application.dto.DrinkRequestDto;
import com.jujeol.drink.drink.domain.Drink;
import com.jujeol.drink.drink.domain.SearchWords;
import com.jujeol.drink.drink.domain.repository.DrinkRepository;
import com.jujeol.drink.drink.exception.NotFoundDrinkException;
import com.jujeol.drink.recommend.application.RecommendStrategy;
import com.jujeol.drink.recommend.domain.RecommendedDrinkResponse;
import com.jujeol.drink.drink.application.dto.SearchDto;
import com.jujeol.member.auth.ui.LoginMember;
import com.jujeol.preference.application.PreferenceService;
import com.jujeol.preference.domain.Preference;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DrinkService {

    private final DrinkRepository drinkRepository;
    private final CategoryRepository categoryRepository;
    private final PreferenceService preferenceService;

    public Page<DrinkDto> showAllDrinksByPage(Pageable pageable,
                                              LoginMember loginMember, String category) {

        Page<Drink> drinks;

        if (category == null || category.isEmpty() || category.equals("ALL")) {
            drinks = drinkRepository.findAll(pageable);
        } else {
            drinks = drinkRepository.findAllByCategory(category, pageable);
        }

        return drinksWithMemberPreference(loginMember, drinks);
    }

    public Page<DrinkDto> showDrinksByPreference(String category, Pageable pageable,
                                                 LoginMember loginMember) {

        Page<Drink> drinkPage;

        if (category == null) {
            drinkPage = drinkRepository.findAllSortByPreference(pageable);
        } else {
            drinkPage = drinkRepository.findAllByCategorySorted(category, pageable);
        }

        return drinksWithMemberPreference(loginMember, drinkPage);
    }

    private Page<DrinkDto> drinksWithMemberPreference(LoginMember loginMember, Page<Drink> drinkPage) {
        if(loginMember.isAnonymous()) {
            return drinkPage
                    .map(drink -> DrinkDto.create(
                            drink,
                            Preference.anonymousPreference(drink)));
        }

        List<Long> drinkIds = drinkPage
                .stream().map(Drink::getId)
                .collect(Collectors.toList());

        Map<Long, Preference> preferenceByDrink = preferenceService.findWithDrinkIds(drinkIds, loginMember.getId());

        return drinkPage
                .map(drink -> DrinkDto.create(
                        drink,
                        preferenceByDrink.getOrDefault(
                                drink.getId(),
                                Preference.anonymousPreference(drink)
                        )));
    }

    public Page<DrinkDto> showDrinksByExpect(String category,
                                             RecommendStrategy recommendStrategy,
                                             Pageable pageable, LoginMember loginMember) {
        List<RecommendedDrinkResponse> recommendDrinks = recommendStrategy
                .recommend(category, loginMember.getId(), pageable.getPageSize());

        if (loginMember.isMember()) {
            List<Long> drinkIds = recommendDrinks.stream()
                    .map(drink -> drink.getDrink().getId())
                    .collect(Collectors.toList());

            Map<Long, Preference> preferenceByDrink = preferenceService.findWithDrinkIds(drinkIds, loginMember.getId());

            final List<DrinkDto> drinkDtos = recommendDrinks.stream()
                    .map(drink -> DrinkDto.create(drink.getDrink(),
                            preferenceByDrink.getOrDefault(drink.getDrink().getId(), Preference.anonymousPreference(drink.getDrink())),
                            drink.getExpectedPreference()))
                    .sorted((o1, o2) -> Double
                            .compare(o2.getExpectedPreference(), o1.getExpectedPreference()))
                    .collect(Collectors.toList());

            return new PageImpl<>(drinkDtos, Pageable.ofSize(pageable.getPageSize()),
                    drinkDtos.size());
        }

        List<DrinkDto> drinkDtos = recommendDrinks.stream()
                .map(drink -> DrinkDto.create(drink.getDrink(),
                        Preference.anonymousPreference(drink.getDrink()),
                        drink.getExpectedPreference()))
                .sorted((o1, o2) -> Double
                        .compare(o2.getExpectedPreference(), o1.getExpectedPreference()))
                .collect(Collectors.toList());

        return new PageImpl<>(drinkDtos, Pageable.ofSize(pageable.getPageSize()), drinkDtos.size());
    }

    public DrinkDto showDrinkDetail(Long id) {
        Drink drink = drinkRepository.findByIdWithFetch(id)
                .orElseThrow(NotFoundDrinkException::new);
        Preference preference = Preference.create(drink, 0.0);
        return DrinkDto.create(drink, preference);
    }

    public DrinkDto showDrinkDetail(Long drinkId, Long memberId) {
        Drink drink = drinkRepository.findByIdWithFetch(drinkId)
                .orElseThrow(NotFoundDrinkException::new);
        Preference preference = preferenceService.showByMemberIdAndDrink(memberId, drink);
        return DrinkDto.create(drink, preference);
    }

    public Page<DrinkDto> showDrinksBySearch(SearchDto searchDto, LoginMember loginMember, Pageable pageable) {
        SearchWords searchWords = SearchWords.create(searchDto.getKeyword());
        List<Drink> drinks = new ArrayList<>();
        for (String searchWord : searchWords.getSearchWords()) {
            drinks.addAll(drinkRepository.findByKeyword(searchWord));
        }
        for (String searchWord : searchWords.getSearchWords()) {
            drinks.addAll(drinkRepository.findByCategory(searchWord));
        }
        List<DrinkDto> drinkDtos = drinks.stream()
                .distinct()
                .map(drink -> DrinkDto.create(
                        drink, Preference.create(drink, 0)))
                .collect(Collectors.toList());
        addPreferenceRateForLoginMember(loginMember, drinkDtos);

        int start = (int) pageable.getOffset();
        int end = Math.min(start + pageable.getPageSize(), drinkDtos.size());

        if (start > end) {
            return new PageImpl<>(new ArrayList<>(), pageable, drinkDtos.size());
        }

        return new PageImpl<>(drinkDtos.subList(start, end), pageable, drinkDtos.size());
    }

    private void addPreferenceRateForLoginMember(
            LoginMember loginMember,
            List<DrinkDto> sortedDrinkDtos
    ) {
        if (loginMember.isMember()) {
            for (DrinkDto drinkDto : sortedDrinkDtos) {
                drinkDto.addPreferenceRate(
                        preferenceService.showByMemberIdAndDrink(
                                loginMember.getId(),
                                drinkDto.getId())
                );
            }
        }
    }

    @Transactional
    public void insertDrink(DrinkRequestDto drinkRequest) {
        List<Category> categories = categoryRepository.findAll();
        Category category = findCategory(categories, drinkRequest.getCategoryKey());
        final Drink drink = drinkRequest.toEntity(category);
        drinkRepository.save(drink);
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
                drinkRequest.getImageFilePathDto().toEntity(),
                category,
                drinkRequest.getAlcoholByVolume(),
                drinkRequest.getDescription()
        );
    }

    @Transactional
    public void removeDrink(Long id) {
        preferenceService.deletePreferenceByDrinkId(id);
        drinkRepository.deleteById(id);
    }

}
