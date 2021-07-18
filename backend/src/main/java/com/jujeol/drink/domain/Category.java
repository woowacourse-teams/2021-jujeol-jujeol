package com.jujeol.drink.domain;

import lombok.Getter;

@Getter
public enum Category {

    BEER("맥주"),
    SOJU("소주"),
    WINE("와인"),
    MAKGEOLLI("막걸리");

    private final String name;

    Category(String name) {
        this.name = name;
    }
}
