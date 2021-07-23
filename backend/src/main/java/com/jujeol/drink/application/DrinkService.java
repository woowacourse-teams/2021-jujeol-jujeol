package com.jujeol.drink.application;

import com.jujeol.admin.ui.dto.CategoryRequestDto;
import com.jujeol.drink.application.dto.DrinkDto;
import com.jujeol.drink.application.dto.DrinkRequestDto;
import com.jujeol.drink.domain.Category;
import com.jujeol.drink.domain.Drink;
import com.jujeol.drink.domain.repository.CategoryRepository;
import com.jujeol.drink.domain.repository.DrinkRepository;
import com.jujeol.drink.exception.NotFoundCategoryException;
import com.jujeol.drink.exception.NotFoundDrinkException;
import com.jujeol.member.domain.Member;
import com.jujeol.member.domain.Preference;
import com.jujeol.member.domain.PreferenceRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
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

    public Page<DrinkDto> showDrinks(Pageable pageable) {
        return drinkRepository.findAll(pageable)
                .map(drink -> DrinkDto.from(drink, Preference.from(drink, 0), fileServerUrl));
    }

    public DrinkDto showDrinkDetail(Long id) {
        Drink drink = drinkRepository.findById(id)
                .orElseThrow(NotFoundDrinkException::new);

        Preference preference = Preference.from(drink, 0.0);

        return DrinkDto.from(drink, preference, fileServerUrl);
    }

    public DrinkDto showDrinkDetail(Long drinkId, Long memberId) {
        Drink drink = drinkRepository.findById(drinkId)
                .orElseThrow(NotFoundDrinkException::new);

        Preference preference = preferenceRepository
                .findByMemberIdAndDrinkId(memberId, drinkId)
                .orElseGet(() -> Preference.from(Member.from(memberId), drink, 0.0));

        return DrinkDto.from(drink, preference, fileServerUrl);
    }

    @Transactional
    public void insertDrinks(List<DrinkRequestDto> drinkRequests) {
        final List<Drink> drinks = new ArrayList<>();

        for (DrinkRequestDto drinkRequest : drinkRequests) {
            Category category = categoryRepository.findById(drinkRequest.getCategoryId()).orElseThrow(NotFoundCategoryException::new);
            drinks.add(drinkRequest.toEntity(category));
        }

        drinkRepository.batchInsert(drinks);
    }

    @Transactional
    public void updateDrink(Long id, DrinkRequestDto drinkRequest) {
        final Drink drink = drinkRepository.findById(id).orElseThrow(NotFoundDrinkException::new);

        Category category = categoryRepository.findById(drinkRequest.getCategoryId()).orElseThrow(NotFoundCategoryException::new);

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
