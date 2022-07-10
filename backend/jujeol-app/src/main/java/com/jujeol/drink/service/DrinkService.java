package com.jujeol.drink.service;

import com.jujeol.drink.controller.requeset.AdminDrinkSaveRequest;
import com.jujeol.drink.domain.model.*;
import com.jujeol.drink.domain.reader.DrinkReader;
import com.jujeol.drink.domain.usecase.DrinkRegisterUseCase;
import com.jujeol.drink.domain.usecase.command.DrinkRegisterCommand;
import com.jujeol.drink.rds.repository.DrinkPageRepository;
import com.jujeol.feedback.domain.model.Preference;
import com.jujeol.feedback.domain.reader.PreferenceReader;
import com.jujeol.model.DrinkWithPreference;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DrinkService {

    private final DrinkRegisterUseCase drinkRegisterUseCase;
    private final DrinkReader drinkReader;
    private final PreferenceReader preferenceReader;

    private final DrinkPageRepository drinkPageRepository;

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
    public Page<Drink> getDrinksWithPage(Pageable pageable) {
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
}
