package com.jujeol.drink.domain.model;

import lombok.Getter;

@Getter
public class Category {
    private Long id;
    private String name;
    private String key;

    private Category(Long id, String name, String key) {
        this.id = id;
        this.name = name;
        this.key = key;
    }

    public static Category create(Long id, String name, String key) {
        return new Category(id, name, key);
    }
}
