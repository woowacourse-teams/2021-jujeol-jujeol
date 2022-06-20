package com.jujeol.drink.domain.service;

import com.jujeol.drink.domain.exception.DuplicateCategoryKeyException;
import com.jujeol.drink.domain.exception.DuplicateCategoryNameException;
import com.jujeol.drink.domain.usecase.CategoryRegisterUseCase;
import com.jujeol.drink.domain.usecase.command.CategoryRegisterCommand;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class CategoryCommandExecutor implements CategoryRegisterUseCase {

    private final CategoryRegisterUseCase.CategoryPort registerCategoryPort;

    @Override
    @Transactional
    public void register(CategoryRegisterCommand command) {
        if (registerCategoryPort.existsByKey(command.getKey())) throw new DuplicateCategoryKeyException();
        if (registerCategoryPort.existsByName(command.getName())) throw new DuplicateCategoryNameException();

        registerCategoryPort.createCategory(command.getName(), command.getKey());
    }
}
