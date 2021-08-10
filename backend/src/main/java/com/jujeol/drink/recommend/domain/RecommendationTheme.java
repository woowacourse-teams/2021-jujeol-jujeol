package com.jujeol.drink.recommend.domain;

import java.util.Arrays;
import lombok.Getter;

@Getter
public enum RecommendationTheme {

    BEST("best"),
    PREFERENCE("preference"),
    VIEW_COUNT("view-count"),
    DEFAULT("default");

    private final String theme;

    RecommendationTheme(String theme) {
        this.theme = theme;
    }

    public static RecommendationTheme matches(String theme) {
        return Arrays.stream(values())
                .filter(it -> it.toString().equalsIgnoreCase(theme) || it.getTheme()
                        .equalsIgnoreCase(theme))
                .findAny()
                .orElse(RecommendationTheme.DEFAULT);
    }

    public boolean isBest() {
        return BEST.equals(this);
    }

    public boolean isPreference() {
        return PREFERENCE.equals(this);
    }

    public boolean isViewCount() {
        return VIEW_COUNT.equals(this);
    }

    public boolean isDefault() {
        return DEFAULT.equals(this);
    }
}
