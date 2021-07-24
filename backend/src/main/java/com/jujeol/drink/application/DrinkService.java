package com.jujeol.drink.application;

import com.jujeol.drink.application.dto.DrinkDto;
import com.jujeol.drink.application.dto.DrinkRequestDto;
import com.jujeol.drink.domain.Drink;
import com.jujeol.drink.domain.repository.DrinkRepository;
import com.jujeol.drink.exception.NotFoundDrinkException;
import com.jujeol.member.domain.Member;
import com.jujeol.member.domain.Preference;
import com.jujeol.member.domain.PreferenceRepository;
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

    public Page<DrinkDto> showDrinks(Pageable pageable) {
        return drinkRepository.findAll(pageable)
                .map(drink -> DrinkDto.create(drink, Preference.from(drink, 0), fileServerUrl));
    }

    public DrinkDto showDrinkDetail(Long id) {
        Drink drink = drinkRepository.findById(id)
                .orElseThrow(NotFoundDrinkException::new);

        Preference preference = Preference.from(drink, 0.0);

        return DrinkDto.create(drink, preference, fileServerUrl);
    }

    public DrinkDto showDrinkDetail(Long drinkId, Long memberId) {
        Drink drink = drinkRepository.findById(drinkId)
                .orElseThrow(NotFoundDrinkException::new);

        Preference preference = preferenceRepository
                .findByMemberIdAndDrinkId(memberId, drinkId)
                .orElseGet(() -> Preference.from(Member.from(memberId), drink, 0.0));

        return DrinkDto.create(drink, preference, fileServerUrl);
    }

    @Transactional
    public void insertDrinks(List<DrinkRequestDto> drinkRequests) {
        final List<Drink> drinks = drinkRequests.stream()
                .map(DrinkRequestDto::toEntity)
                .collect(Collectors.toList());
        drinkRepository.batchInsert(drinks);
    }

    @Transactional
    public void updateDrink(Long id, DrinkRequestDto drinkDto) {
        final Drink drink = drinkRepository.findById(id).orElseThrow(NotFoundDrinkException::new);
        drink.updateInfo(drinkDto.getName(),
                drinkDto.getEnglishName(),
                drinkDto.getImageUrl(),
                drinkDto.getCategory(),
                drinkDto.getAlcoholByVolume());
    }

    @Transactional
    public void removeDrink(Long id) {
        drinkRepository.deleteById(id);
    }
}
