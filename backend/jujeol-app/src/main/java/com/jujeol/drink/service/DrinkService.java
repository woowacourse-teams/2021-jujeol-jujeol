package com.jujeol.drink.service;

import com.jujeol.drink.controller.requeset.AdminDrinkSaveRequest;
import com.jujeol.drink.domain.model.*;
import com.jujeol.drink.domain.reader.DrinkReader;
import com.jujeol.drink.domain.usecase.DrinkRegisterUseCase;
import com.jujeol.drink.domain.usecase.command.DrinkRegisterCommand;
import com.jujeol.drink.rds.repository.DrinkPageRepository;
import com.jujeol.feedback.domain.model.Preference;
import com.jujeol.feedback.domain.reader.PreferenceReader;
import com.jujeol.feedback.rds.repository.PreferencePageRepository;
import com.jujeol.model.DrinkWithPreference;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

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
    public void saveDrink(AdminDrinkSaveRequest adminDrinkSaveRequest, ImageFilePath imageFilePath) {
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
    public Optional<DrinkWithPreference> findDrinkWithPreference(Long drinkId, Long memberId) {
        Optional<Drink> foundDrink = drinkReader.findById(drinkId);
        if (foundDrink.isEmpty()) {
            return Optional.empty();
        }

        Optional<Preference> foundPreference = preferenceReader.findByDrinkIdAndMemberId(drinkId, memberId);
        if (foundPreference.isEmpty()) {
            return Optional.empty();
        }

        return Optional.of(DrinkWithPreference.create(foundDrink.get(), foundPreference.get()));
    }

    @Transactional(readOnly = true)
    @Deprecated
    public Page<DrinkWithPreference> findDrinksWithPreferencePage(Long memberId, Pageable pageable) {
        Page<Preference> preferencePage = preferencePageRepository.findByMemberId(memberId, pageable);
        Pageable resultPageable = preferencePage.getPageable();
        long totalElements = preferencePage.getTotalElements();
        Map<Long, List<Preference>> preferenceByDrinkId = preferencePage.stream().collect(groupingBy(Preference::getDrinkId));

        List<DrinkWithPreference> contents = drinkReader.findAllByDrinkIdIn(preferenceByDrinkId.keySet())
            .stream()
            .flatMap(drink -> preferenceByDrinkId.get(drink.getDrinkId())
                .stream()
                .map(preference -> DrinkWithPreference.create(drink, preference)))
            .collect(Collectors.toList());

        return new PageImpl<>(contents, resultPageable, totalElements);
    }
}
