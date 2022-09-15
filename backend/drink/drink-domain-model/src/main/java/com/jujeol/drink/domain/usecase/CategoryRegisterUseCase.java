package com.jujeol.drink.domain.usecase;

import com.jujeol.drink.domain.exception.DuplicateCategoryKeyException;
import com.jujeol.drink.domain.exception.DuplicateCategoryNameException;
import com.jujeol.drink.domain.usecase.command.CategoryRegisterCommand;

public interface CategoryRegisterUseCase {

    void register(CategoryRegisterCommand command) throws DuplicateCategoryKeyException, DuplicateCategoryNameException;

    interface CategoryPort {
        void createCategory(String name, String key);
        boolean existsByKey(String key);
        boolean existsByName(String name);
    }
}
