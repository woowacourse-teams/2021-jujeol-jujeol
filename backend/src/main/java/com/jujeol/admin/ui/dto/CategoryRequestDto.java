package com.jujeol.admin.ui.dto;

import com.jujeol.drink.domain.Category;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class CategoryRequestDto {

    private Long id;
    private String name;

    public static CategoryRequestDto create(Long id) {
        return new CategoryRequestDto(id, null);
    }

    public Category toEntity() {
        return Category.create(this.id, this.name, null);
    }
}
