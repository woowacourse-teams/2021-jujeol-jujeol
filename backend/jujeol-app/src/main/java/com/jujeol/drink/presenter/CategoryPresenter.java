package com.jujeol.drink.presenter;

import com.jujeol.drink.controller.requeset.CategoryResponse;
import com.jujeol.drink.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class CategoryPresenter {

    private final CategoryService categoryService;

    public List<CategoryResponse> showCategories() {
        return categoryService.findCategories()
            .stream()
            .map(category -> CategoryResponse.builder()
                .id(category.getId())
                .key(category.getKey())
                .name(category.getName())
                .build())
            .collect(Collectors.toList());
    }
}
