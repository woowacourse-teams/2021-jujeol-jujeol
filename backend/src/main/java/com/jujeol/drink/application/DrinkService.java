package com.jujeol.drink.application;

import static com.jujeol.drink.domain.RecommendationTheme.PREFERENCE;
import static com.jujeol.drink.domain.RecommendationTheme.VIEW_COUNT;

import com.jujeol.drink.application.dto.AdminDrinkRequestDto;
import com.jujeol.drink.application.dto.DrinkDto;
import com.jujeol.drink.domain.Category;
import com.jujeol.drink.domain.Drink;
import com.jujeol.drink.domain.RecommendationTheme;
import com.jujeol.drink.domain.ViewCount;
import com.jujeol.drink.domain.repository.CategoryRepository;
import com.jujeol.drink.domain.repository.DrinkRepository;
import com.jujeol.drink.exception.NotFoundCategoryException;
import com.jujeol.drink.exception.NotFoundDrinkException;
import com.jujeol.member.domain.Preference;
import com.jujeol.member.domain.PreferenceRepository;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
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

    public Page<DrinkDto> showDrinks(String theme, Pageable pageable) {
        RecommendationTheme recommendationTheme = RecommendationTheme.matches(theme);
        if (PREFERENCE.equals(recommendationTheme)) {
            return drinkRepository.findAllOrderByPreferenceAvg(pageable)
                    .map(drink -> DrinkDto.create(drink, Preference.from(drink, 0), fileServerUrl));
        }
        if (VIEW_COUNT.equals(recommendationTheme)) {
            return drinkRepository.findAllOrderByViewCount(pageable)
                    .map(drink -> DrinkDto.create(drink, Preference.from(drink, 0), fileServerUrl));
        }
        return drinkRepository.findAll(pageable)
                .map(drink -> DrinkDto.create(drink, Preference.from(drink, 0), fileServerUrl));
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
    public void insertDrinks(List<AdminDrinkRequestDto> drinkRequests) {
        final List<Drink> drinks = new ArrayList<>();

        List<Category> categories = categoryRepository.findAll();
        for (AdminDrinkRequestDto drinkRequest : drinkRequests) {
            Category category = findCategory(categories, drinkRequest.getCategoryId());
            ViewCount viewCount = viewCountService.insert(ViewCount.create(0L));
            drinks.add(drinkRequest.toEntity(category, viewCount));
        }
        drinkRepository.batchInsert(drinks);
    }

    private Category findCategory(List<Category> categories, Long categoryId) {
        return categories.stream()
                .filter(category -> category.isEqual(categoryId))
                .findAny()
                .orElseThrow(NotFoundCategoryException::new);
    }

    @Transactional
    public void updateDrink(Long id, AdminDrinkRequestDto drinkRequest) {
        final Drink drink = drinkRepository.findById(id).orElseThrow(NotFoundDrinkException::new);

        Category category = categoryRepository.findById(drinkRequest.getCategoryId())
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
