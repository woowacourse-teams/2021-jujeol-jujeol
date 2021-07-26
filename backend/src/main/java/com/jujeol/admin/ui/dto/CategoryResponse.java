package com.jujeol.admin.ui.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class CategoryResponse {

    private Long id;
    private String name;

    public void changeCategory(CategoryRequestDto category) {
        this.id = category.getId();
        this.name = category.getName();
    }
}
