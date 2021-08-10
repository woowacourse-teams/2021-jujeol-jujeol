package com.jujeol.drink.category.ui;

import com.jujeol.commons.dto.CommonResponse;
import com.jujeol.drink.category.application.CategoryService;
import com.jujeol.drink.category.ui.dto.CategoryResponse;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping("/categories")
    public ResponseEntity<CommonResponse<List<CategoryResponse>>> showCategories() {
        List<CategoryResponse> categories = categoryService.showCategories()
                .stream()
                .map(CategoryResponse::create)
                .collect(Collectors.toList());
        return ResponseEntity.ok(CommonResponse.from(categories));
    }
}
