package com.jujeol.drink.application.dto;

import com.jujeol.drink.domain.Category;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class CategoryDto {

    private Long id;
    private String name;
    private String key;

    public static CategoryDto create(Category category) {
        return new CategoryDto(category.getId(), category.getName(), category.getKey());
    }
}
