package com.jujeol.drink.domain.model;

import com.jujeol.drink.domain.exception.InvalidDrinkNameException;

import java.util.Objects;

public class DrinkName {

    private String name;

    private DrinkName(String name) {
        if (Objects.isNull(name) || name.isBlank()) {
            throw new InvalidDrinkNameException();
        }
        this.name = name;
    }

    public String value() {
        return name;
    }

    public static DrinkName from(String name) {
        return new DrinkName(name);
    }
}
