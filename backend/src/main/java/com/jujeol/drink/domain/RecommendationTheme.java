package com.jujeol.drink.domain;

import java.util.Arrays;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
public enum RecommendationTheme {

    ALL("all"),
    PREFERENCE("preference"),
    WEATHER("weather"),
    VIEW_COUNT("view-count");

    private final String theme;

    RecommendationTheme(String theme) {
        this.theme = theme;
    }

    public static RecommendationTheme matches(String theme) {
        return Arrays.stream(values())
                .filter(it -> it.toString().equalsIgnoreCase(theme) || it.getTheme()
                        .equalsIgnoreCase(theme))
                .findAny()
                .orElse(RecommendationTheme.ALL);
    }
}
