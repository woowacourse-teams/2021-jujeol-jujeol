package com.jujeol.drink.ui.dto;

import com.jujeol.drink.application.dto.SearchDto;
import lombok.AllArgsConstructor;
import lombok.Builder.Default;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SearchRequest {

    private String search;
    private String category;

    public SearchDto toDto() {
        return SearchDto.create(search, category);
    }
}
