package com.jujeol.preference.application;

import com.jujeol.drink.drink.domain.Drink;
import com.jujeol.drink.drink.domain.repository.DrinkRepository;
import com.jujeol.drink.drink.exception.NotFoundDrinkException;
import com.jujeol.member.member.application.dto.PreferenceDto;
import com.jujeol.preference.domain.Preference;
import com.jujeol.preference.domain.PreferenceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PreferenceService {

    private final PreferenceRepository preferenceRepository;
    private final DrinkRepository drinkRepository;

    public Page<Preference> showPreferenceByMemberId(Long memberId, Pageable pageable) {
        return preferenceRepository.findByMemberIdOrderByCreatedAtDesc(memberId, pageable);
    }

    public Preference showByMemberIdAndDrink(Long memberId, Drink drink) {
        return preferenceRepository
                .findByMemberIdAndDrinkId(memberId, drink.getId())
                .orElseGet(() -> Preference.anonymousPreference(drink));
    }

    public Preference showByMemberIdAndDrink(Long memberId, Long drinkId) {
        return preferenceRepository
                .findByMemberIdAndDrinkId(memberId, drinkId)
                .orElseGet(() -> Preference.anonymousPreference(drinkId));
    }

    @Transactional
    public void createOrUpdatePreference(
            Long memberId,
            Long drinkId,
            PreferenceDto preferenceDto
    ) {
        Drink drink = drinkRepository.findById(drinkId)
                .orElseThrow(NotFoundDrinkException::new);

        preferenceRepository
                .findByMemberIdAndDrinkId(memberId, drink.getId())
                .ifPresentOrElse(exist -> exist.updateRate(preferenceDto.getPreferenceRate()),
                        () -> {
                            Preference newPreference = Preference
                                    .create(memberId, drink, preferenceDto.getPreferenceRate());
                            preferenceRepository.save(newPreference);
                        }
                );

        updatePreferenceAverage(drinkId, drink);
    }

    @Transactional
    public void deletePreference(Long memberId, Long drinkId) {
        Drink drink = drinkRepository.findById(drinkId)
                .orElseThrow(NotFoundDrinkException::new);

        preferenceRepository.deleteByMemberIdAndDrinkId(memberId, drinkId);

        updatePreferenceAverage(drinkId, drink);
    }

    private void updatePreferenceAverage(Long drinkId, Drink drink) {
        Double average = preferenceRepository.averageOfPreferenceRate(drinkId).orElse(0.0);
        drink.updateAverage(average);
    }
}
