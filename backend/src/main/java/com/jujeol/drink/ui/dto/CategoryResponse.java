package com.jujeol.drink.ui.dto;

import com.jujeol.drink.application.dto.CategoryDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CategoryResponse {

    private Long id;
    private String category;
    private String categoryKey;

    public static CategoryResponse create(CategoryDto categoryDto) {
        return new CategoryResponse(categoryDto.getId(), categoryDto.getName(), categoryDto.getKey());
    }
}
