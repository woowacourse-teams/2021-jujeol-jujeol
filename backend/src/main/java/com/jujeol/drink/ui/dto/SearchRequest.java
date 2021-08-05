package com.jujeol.drink.ui.dto;

import com.jujeol.drink.application.dto.SearchDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class SearchRequest {

    private String search;
    private String category;

    public SearchDto toDto() {
        return SearchDto.create(search, category);
    }
}
