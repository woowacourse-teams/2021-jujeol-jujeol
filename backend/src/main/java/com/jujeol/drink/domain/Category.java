package com.jujeol.drink.domain;

import com.jujeol.drink.exception.NotFoundCategoryException;
import java.util.Arrays;
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

    public static Category matches(String category) {
        return Arrays.stream(values())
                .filter(cat -> cat.toString().equalsIgnoreCase(category) || cat.getName()
                        .equalsIgnoreCase(category))
                .findAny()
                .orElseThrow(NotFoundCategoryException::new);
    }
}
