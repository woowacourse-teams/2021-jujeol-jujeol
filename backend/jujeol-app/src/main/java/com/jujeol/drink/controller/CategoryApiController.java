package com.jujeol.drink.controller;

import com.jujeol.commons.CommonResponse;
import com.jujeol.drink.controller.requeset.CategoryResponse;
import com.jujeol.drink.presenter.CategoryPresenter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CategoryApiController {

    private final CategoryPresenter categoryPresenter;

    @GetMapping("/categories")
    public ResponseEntity<CommonResponse<List<CategoryResponse>>> showCategories() {
        return ResponseEntity.ok(CommonResponse.from(categoryPresenter.showCategories()));
    }
}
