package com.jujeol.drink.acceptance;

import static java.util.stream.Collectors.toList;
import static org.assertj.core.api.Assertions.assertThat;

import com.jujeol.AcceptanceTest;
import com.jujeol.drink.domain.Category;
import com.jujeol.drink.ui.dto.CategoryResponse;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class CategoryAcceptanceTest extends AcceptanceTest {

    @Autowired
    public CategoryAcceptanceTool categoryAcceptanceTool;

    @Test
    void showAll() {
        //when
        List<CategoryResponse> actual = request().get("/categories")
                .withDocument("category/show/all")
                .build()
                .convertBodyToList(CategoryResponse.class);

        //then
        final List<String> categoryNames =
                categoryAcceptanceTool.기본_카테고리()
                        .stream()
                        .map(Category::getName)
                        .collect(toList());

        assertThat(actual).extracting("name").containsExactlyElementsOf(categoryNames);
    }
}
