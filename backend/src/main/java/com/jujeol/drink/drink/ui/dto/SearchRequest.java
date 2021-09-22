package com.jujeol.drink.drink.ui.dto;

import com.jujeol.drink.drink.application.dto.SearchDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SearchRequest {

    private String keyword;
    private String category;

    public SearchDto toDto() {
        return SearchDto.create(keyword, category);
    }
}
