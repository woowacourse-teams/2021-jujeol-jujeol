package com.jujeol.test.api.service;

import com.jujeol.review.exception.NotExistReviewInDrinkException;
import com.jujeol.test.domain.model.usecase.DrinkChangeNameUseCase;
import com.jujeol.test.domain.model.usecase.DrinkRegistrationUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class DrinkService {

    private final DrinkRegistrationUseCase drinkRegistrationUseCase;
    private final DrinkChangeNameUseCase drinkChangeNameUseCase;

    @Transactional
    public void registerDrink(String serviceModel) throws NotExistReviewInDrinkException {
        // adapter
        drinkRegistrationUseCase.register(serviceModel.command());
    }


    public void changeName(String name) {
        drinkChangeNameUseCase.changeName(name);
    }
}
