package com.jujeol.test.domain.model.usecase;

public interface DrinkChangeNameUseCase {

    void changeName(String name);

    interface DrinkPort {
        void modifyName(String name);
    }
}
