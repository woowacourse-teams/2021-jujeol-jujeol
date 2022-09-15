package com.jujeol.drink.controller.requeset;

import lombok.Builder;
import lombok.Getter;

@Getter
public class CategoryResponse {

    private Long id;
    private String name;
    private String key;

    @Builder
    public CategoryResponse(Long id, String name, String key) {
        this.id = id;
        this.name = name;
        this.key = key;
    }
}
