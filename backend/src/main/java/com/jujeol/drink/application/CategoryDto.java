package com.jujeol.drink.application;

import com.jujeol.drink.domain.Category;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class CategoryDto {

    private Long id;
    private String name;
    private String key;

    public static CategoryDto create(Category category){
        return new CategoryDto(category.getId(), category.getName(), category.getKey());
    }

    public Category toEntity(){
        return Category.create(id, name, null);
    }
}
