package com.jujeol.drink.application.dto;

import com.jujeol.drink.domain.Search;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
public class SearchDto {

    private String search;
    private String categoryKey;

    public static SearchDto create(String search, String categoryKey) {
        return new SearchDto(search, categoryKey);
    }

    public Search toDomain() {
        return Search.create(search, categoryKey);
    }
}
