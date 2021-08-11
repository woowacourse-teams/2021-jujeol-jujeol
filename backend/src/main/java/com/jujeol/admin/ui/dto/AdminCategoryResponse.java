package com.jujeol.admin.ui.dto;

import com.jujeol.drink.category.application.dto.CategoryDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class AdminCategoryResponse {

    private Long id;
    private String name;
    private String key;

    public static AdminCategoryResponse create(CategoryDto categoryDto) {
        return new AdminCategoryResponse(categoryDto.getId(), categoryDto.getName(),
                categoryDto.getKey());
    }
}
