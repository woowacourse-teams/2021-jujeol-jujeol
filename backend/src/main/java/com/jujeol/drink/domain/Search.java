package com.jujeol.drink.domain;

import java.util.Objects;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Search {

    private final String search;
    private final String categoryKey;


    public static Search create(String search, String categoryKey) {
        return new Search(
                Objects.requireNonNullElse(search, ""),
                Objects.requireNonNullElse(categoryKey, "")
        );
    }

    public boolean hasSearch() {
        return !search.isBlank();
    }

    public boolean hasCategoryKey() {
        return !categoryKey.isBlank();
    }

    public String getUpperCategoryKey() {
        return categoryKey.toUpperCase();
    }
}
