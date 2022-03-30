package com.jujeol.test.data.rds.repository;

import com.jujeol.test.data.rds.entity.DrinkRepository;
import com.jujeol.test.domain.model.usecase.DrinkChangeNameUseCase;
import com.jujeol.test.domain.model.usecase.DrinkRegistrationUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DrinkDomainRepository implements DrinkChangeNameUseCase.DrinkPort {

    private final DrinkRepository drinkRepository;

    @Override
    public void modifyName(String name) {

    }
}
