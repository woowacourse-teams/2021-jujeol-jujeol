package com.jujeol.elasticsearch.ui.dto;

import com.jujeol.elasticsearch.application.dto.SearchDto;
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

    public SearchDto toDto() {
        return SearchDto.create(keyword);
    }
}
