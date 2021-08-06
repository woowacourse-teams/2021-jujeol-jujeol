package com.jujeol.drink.ui.dto;

import com.jujeol.drink.application.dto.CategoryDto;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

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
