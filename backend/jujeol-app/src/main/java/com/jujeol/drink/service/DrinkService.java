package com.jujeol.drink.service;

import com.jujeol.drink.controller.requeset.AdminDrinkSaveRequest;
import com.jujeol.drink.domain.exception.NotFoundCategoryException;
import com.jujeol.drink.domain.model.AlcoholByVolume;
import com.jujeol.drink.domain.model.Description;
import com.jujeol.drink.domain.model.Drink;
import com.jujeol.drink.domain.model.DrinkEnglishName;
import com.jujeol.drink.domain.model.DrinkName;
import com.jujeol.drink.domain.model.DrinkSort;
import com.jujeol.drink.domain.model.ImageFilePath;
import com.jujeol.drink.domain.reader.DrinkReader;
import com.jujeol.drink.domain.usecase.DrinkRegisterUseCase;
import com.jujeol.drink.domain.usecase.command.DrinkRegisterCommand;
import com.jujeol.drink.rds.repository.DrinkPageRepository;
import com.jujeol.feedback.domain.model.Preference;
import com.jujeol.feedback.domain.reader.PreferenceReader;
import com.jujeol.feedback.rds.repository.PreferencePageRepository;
import com.jujeol.model.DrinkWithMemberPreference;
import com.jujeol.model.PreferenceWithDrink;
import com.jujeol.model.SearchWords;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import static java.util.stream.Collectors.groupingBy;

@Service
@RequiredArgsConstructor
public class DrinkService {

    private final DrinkRegisterUseCase drinkRegisterUseCase;
    private final DrinkReader drinkReader;
    private final PreferenceReader preferenceReader;

    private final DrinkPageRepository drinkPageRepository;
    private final PreferencePageRepository preferencePageRepository;

    @Transactional
    public void saveDrink(AdminDrinkSaveRequest adminDrinkSaveRequest, ImageFilePath imageFilePath) throws NotFoundCategoryException {
        DrinkRegisterCommand command = DrinkRegisterCommand.builder()
            .name(DrinkName.from(adminDrinkSaveRequest.getName()))
            .englishName(DrinkEnglishName.from(adminDrinkSaveRequest.getEnglishName()))
            .alcoholByVolume(AlcoholByVolume.from(adminDrinkSaveRequest.getAlcoholByVolume()))
            .categoryKey(adminDrinkSaveRequest.getCategoryKey())
            .description(Description.from(adminDrinkSaveRequest.getDescription()))
            .imageFilePath(imageFilePath)
            .build();
        drinkRegisterUseCase.register(command);
    }

    @Transactional(readOnly = true)
    @Deprecated
    public Page<Drink> getDrinksPage(Pageable pageable) {
        return drinkPageRepository.findAll(pageable);
    }

    @Transactional(readOnly = true)
    public Optional<Drink> findDrink(Long id) {
        return drinkReader.findById(id);
    }

    @Transactional(readOnly = true)
    public Optional<PreferenceWithDrink> findDrinkWithPreference(Long drinkId, Long memberId) {
        Optional<Drink> foundDrink = drinkReader.findById(drinkId);
        if (foundDrink.isEmpty()) {
            return Optional.empty();
        }

        Optional<Preference> foundPreference = preferenceReader.findByDrinkIdAndMemberId(drinkId, memberId);
        if (foundPreference.isEmpty()) {
            return Optional.empty();
        }

        return Optional.of(PreferenceWithDrink.create(foundDrink.get(), foundPreference.get()));
    }

    @Transactional(readOnly = true)
    @Deprecated
    public Page<PreferenceWithDrink> findDrinksWithPreferencePage(Long memberId, Pageable pageable) {
        Page<Preference> preferencePage = preferencePageRepository.findByMemberId(memberId, pageable);
        Pageable resultPageable = preferencePage.getPageable();
        long totalElements = preferencePage.getTotalElements();
        Map<Long, List<Preference>> preferenceByDrinkId = preferencePage.stream().collect(groupingBy(Preference::getDrinkId));

        List<PreferenceWithDrink> contents = drinkReader.findAllByDrinkIdIn(preferenceByDrinkId.keySet())
            .stream()
            .flatMap(drink -> preferenceByDrinkId.get(drink.getDrinkId())
                .stream()
                .map(preference -> PreferenceWithDrink.create(drink, preference)))
            .collect(Collectors.toList());

        return new PageImpl<>(contents, resultPageable, totalElements);
    }

    // search 일 때는 expectedPreference 를 계산 안 함?!
    @Transactional(readOnly = true)
    public Page<DrinkWithMemberPreference> searchForMember(Long memberId, SearchWords searchWords, Pageable pageable) {
        Page<Drink> drinks = drinkPageRepository.findByKeywords(searchWords.getSearchWords(), pageable);
        List<Long> drinkIds = drinks.stream().map(Drink::getDrinkId).collect(Collectors.toList());

        Map<Long, Preference> preferenceByDrinkId = new HashMap<>();
        preferenceReader.findByMemberIdAndDrinkIdIn(memberId, drinkIds)
            .forEach(preference -> preferenceByDrinkId.put(preference.getDrinkId(), preference));

        return drinks.map(drink ->
            DrinkWithMemberPreference.create(
                drink,
                preferenceByDrinkId.getOrDefault(drink.getDrinkId(), Preference.anonymousPreference(drink.getDrinkId())),
                0.0
            )
        );
    }

    @Transactional(readOnly = true)
    public Page<Drink> searchForAnonymous(SearchWords searchWords, Pageable pageable) {
        return drinkPageRepository.findByKeywords(searchWords.getSearchWords(), pageable);
    }

    @Transactional(readOnly = true)
    public Page<DrinkWithMemberPreference> findDrinkListWithPreference(String category, Long memberId, DrinkSort drinkSort, Pageable pageable) {
        Page<Drink> drinks;
        if (StringUtils.hasText(category)) {
            drinks = drinkPageRepository.findByCategory(drinkSort, category, pageable);
        } else {
            drinks = drinkPageRepository.findAll(drinkSort, pageable);
        }

        List<Long> drinkIds = drinks.stream().map(Drink::getDrinkId).collect(Collectors.toList());

        Map<Long, Preference> preferenceByDrinkId = new HashMap<>();
        preferenceReader.findByMemberIdAndDrinkIdIn(memberId, drinkIds)
            .forEach(preference -> preferenceByDrinkId.put(preference.getDrinkId(), preference));

        return drinks.map(drink ->
            DrinkWithMemberPreference.create(
                drink,
                preferenceByDrinkId.getOrDefault(drink.getDrinkId(), Preference.anonymousPreference(drink.getDrinkId())),
                0.0
            )
        );
    }

    @Transactional(readOnly = true)
    public Page<Drink> findDrinkList(String category, DrinkSort drinkSort, Pageable pageable) {
        if (StringUtils.hasText(category)) {
            return drinkPageRepository.findByCategory(drinkSort, category, pageable);
        } else {
            return drinkPageRepository.findAll(drinkSort, pageable);
        }
    }

    @Transactional(readOnly = true)
    // TODO : 주류 추천 등록하기 (리팩토링 필요)
    public Page<DrinkWithMemberPreference> recommendDrink(String category, Long memberId, Pageable pageable) {
        Page<Drink> drinks;
        if (StringUtils.hasText(category)) {
            drinks = drinkPageRepository.findByCategory(DrinkSort.PREFERENCE_AVG, category, pageable);
        } else {
            drinks = drinkPageRepository.findAll(DrinkSort.PREFERENCE_AVG, pageable);
        }

        List<Long> drinkIds = drinks.stream().map(Drink::getDrinkId).collect(Collectors.toList());

        Map<Long, Preference> preferenceByDrinkId = new HashMap<>();
        preferenceReader.findByMemberIdAndDrinkIdIn(memberId, drinkIds)
            .forEach(preference -> preferenceByDrinkId.put(preference.getDrinkId(), preference));

        return drinks.map(drink ->
            DrinkWithMemberPreference.create(
                drink,
                preferenceByDrinkId.getOrDefault(drink.getDrinkId(), Preference.anonymousPreference(drink.getDrinkId())),
                drink.getPreferenceAvg()
            )
        );
    }
}
