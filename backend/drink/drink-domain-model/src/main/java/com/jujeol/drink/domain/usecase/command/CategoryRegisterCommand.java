package com.jujeol.drink.domain.usecase.command;

import lombok.Getter;

@Getter
public class CategoryRegisterCommand {

    private String name;
    private String key;

    private CategoryRegisterCommand(String name, String key) {
        this.name = name;
        this.key = key;
    }

    public static CategoryRegisterCommand create(String name, String key) {
        return new CategoryRegisterCommand(name, key);
    }
}
