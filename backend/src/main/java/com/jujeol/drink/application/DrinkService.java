package com.jujeol.drink.application;

import com.jujeol.drink.application.dto.DrinkDto;
import com.jujeol.drink.application.dto.DrinkRequestDto;
import com.jujeol.drink.application.dto.SearchDto;
import com.jujeol.drink.domain.Category;
import com.jujeol.drink.domain.Drink;
import com.jujeol.drink.domain.RecommendState;
import com.jujeol.drink.domain.ViewCount;
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

    private final ViewCountService viewCountService;

    public Page<DrinkDto> showDrinksBySearch(SearchDto searchDto, Pageable pageable) {
        if(searchDto.getSearch()==null){
            return drinkRepository.findAll(pageable)
                    .map(drink -> DrinkDto.create(drink, Preference.from(drink, 0), fileServerUrl));
        }
        Category category = categoryRepository.findByKey(searchDto.getCategoryKey())
                .orElseThrow(NotFoundCategoryException::new);
        Drink OB = Drink.create(
                "오비", "OB", 85.0,
                "KakaoTalk_Image_2021-07-08-19-58-22_007.png",
                0.0, category);

        DrinkDto OBDto = DrinkDto.create(OB, Preference.anonymousPreference(OB), fileServerUrl);
        return new PageImpl<>(List.of(OBDto), pageable, 1L);
    }

    public Page<DrinkDto> showAllDrinksByPage(Pageable pageable) {
        List<DrinkDto> drinkDtos = drinkRepository.findAll(pageable).stream()
                .map(drink -> DrinkDto.create(
                        drink, Preference.from(drink, 0), fileServerUrl))
        .collect(Collectors.toList());

        return new PageImpl<>(drinkDtos, pageable, drinkDtos.size());
    }
    public Page<DrinkDto> showRecommendDrinks(RecommendState recommendState, Pageable pageable, LoginMember loginMember) {
        List<Drink> recommendDrinks = recommendState.recommend(loginMember.getId(), pageable.getPageSize());

        List<DrinkDto> drinkDtos = recommendDrinks.stream()
                .map(drink -> DrinkDto.create(
                        drink, Preference.from(drink, 0), fileServerUrl))
                .collect(Collectors.toList());

        return new PageImpl<>(drinkDtos, pageable, drinkDtos.size());
    }

    @Transactional
    public DrinkDto showDrinkDetail(Long id) {
        Drink drink = drinkRepository.findById(id)
                .orElseThrow(NotFoundDrinkException::new);
        Preference preference = Preference.from(drink, 0.0);
        viewCountService.updateViewCount(drink);
        return DrinkDto.create(drink, preference, fileServerUrl);
    }

    @Transactional
    public DrinkDto showDrinkDetail(Long drinkId, Long memberId) {
        Drink drink = drinkRepository.findById(drinkId)
                .orElseThrow(NotFoundDrinkException::new);
        Preference preference = preferenceRepository
                .findByMemberIdAndDrinkId(memberId, drinkId)
                .orElseGet(() -> Preference.anonymousPreference(drink));
        viewCountService.updateViewCount(drink);
        return DrinkDto.create(drink, preference, fileServerUrl);
    }

    @Transactional
    public void insertDrinks(List<DrinkRequestDto> drinkRequests) {
        final List<Drink> drinks = new ArrayList<>();

        List<Category> categories = categoryRepository.findAll();
        for (DrinkRequestDto drinkRequest : drinkRequests) {
            Category category = findCategory(categories, drinkRequest.getCategoryKey());
            ViewCount viewCount = viewCountService.insert(ViewCount.create(0L));
            drinks.add(drinkRequest.toEntity(category, viewCount));
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
