package com.jujeol.drink.ui;

import com.jujeol.commons.dto.CommonResponse;
import com.jujeol.drink.application.CategoryService;
import com.jujeol.drink.application.dto.CategoryDto;
import com.jujeol.drink.domain.Category;
import com.jujeol.drink.ui.dto.CategoryResponse;
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
        List<CategoryResponse> categories = List.of(
                CategoryDto.create(Category.create(1L, "맥주", "BEER")),
                CategoryDto.create(Category.create(2L, "소주", "SOJU"))
        )
                .stream()
                .map(CategoryResponse::create)
                .collect(Collectors.toList());
        return ResponseEntity.ok(CommonResponse.from(categories));
    }
}
