package com.jujeol.drink.domain.usecase;

import com.jujeol.drink.domain.usecase.command.CategoryRegisterCommand;

public interface CategoryRegisterUseCase {

    void register(CategoryRegisterCommand command);

    interface CategoryPort {
        void createCategory(String name, String key);
    }
}
