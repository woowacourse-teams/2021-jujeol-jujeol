package com.jujeol.drink.application;

import com.jujeol.drink.application.dto.DrinkDto;
import com.jujeol.drink.domain.Drink;
import com.jujeol.drink.domain.DrinkRepository;
import com.jujeol.drink.domain.ReviewRepository;
import com.jujeol.drink.exception.NotFoundDrinkException;
import com.jujeol.member.domain.Preference;
import com.jujeol.member.domain.PreferenceRepository;
import com.jujeol.member.exception.NoSuchPreferenceException;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DrinkService {

    @Value("${file-server.url:}")
    private String fileServerUrl;

    private final DrinkRepository drinkRepository;
    private final PreferenceRepository preferenceRepository;
    private final ReviewRepository reviewRepository;

    public List<DrinkDto> showDrinks() {
        return drinkRepository.findAll(Pageable.ofSize(7))
                .get()
                .map(drink -> DrinkDto.from(drink, Preference.of(drink, 0), fileServerUrl))
                .collect(Collectors.toList());
    }

    public DrinkDto showDrinkDetail(Long id) {
        Drink drink = drinkRepository.findById(id)
                .orElseThrow(NotFoundDrinkException::new);

        Preference preference = Preference.of(drink, 0.0);

        return DrinkDto.from(drink, preference, fileServerUrl);
    }

    public DrinkDto showDrinkDetail(Long drinkId, Long memberId) {
        Drink drink = drinkRepository.findById(drinkId)
                .orElseThrow(NotFoundDrinkException::new);

        Preference preference = preferenceRepository
                .findByMemberIdAndDrinkId(memberId, drinkId)
                .orElseThrow(NoSuchPreferenceException::new);

        return DrinkDto.from(drink, preference, fileServerUrl);
    }
}
