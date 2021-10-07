package com.jujeol.drink.drink.application.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class SearchDto {

    private String keyword;
    private String categoryKey;

    public static SearchDto create(String keyword, String categoryKey) {
        return new SearchDto(keyword, categoryKey);
    }
}
