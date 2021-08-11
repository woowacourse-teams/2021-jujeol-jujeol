package com.jujeol.drink.category.ui.dto;

import com.jujeol.drink.category.application.dto.CategoryDto;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class CategoryResponse {

    private Long id;
    private String name;
    private String key;

    public static CategoryResponse create(CategoryDto categoryDto) {
        return new CategoryResponse(
                categoryDto.getId(),
                categoryDto.getName(),
                categoryDto.getKey()
        );
    }

    public static CategoryResponse create(Long id, String name, String key) {
        return new CategoryResponse(id, name, key);
    }
}
