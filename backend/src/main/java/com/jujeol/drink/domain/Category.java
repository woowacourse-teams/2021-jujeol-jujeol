package com.jujeol.drink.domain;

public enum Category {

    BEER("맥주"),
    SOJU("소주"),
    WINE("와인"),
    MAKGEOLLI("막걸리");

    private String name;

    Category(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
