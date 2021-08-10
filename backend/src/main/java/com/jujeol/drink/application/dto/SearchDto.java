package com.jujeol.drink.application.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class SearchDto {

    private String search;
    private String categoryKey;

    public static SearchDto create(String search, String categoryKey) {
        return new SearchDto(search, categoryKey);
    }
}
