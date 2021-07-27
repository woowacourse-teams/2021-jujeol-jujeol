package com.jujeol.drink.acceptance;

import static org.assertj.core.api.Assertions.assertThat;

import com.jujeol.AcceptanceTest;
import com.jujeol.TestDataLoader;
import com.jujeol.drink.ui.dto.CategoryResponse;
import java.util.List;
import java.util.stream.Collectors;
import org.junit.jupiter.api.Test;

public class CategoryAcceptanceTest extends AcceptanceTest {

    @Test
    void showAll() {
        //when
        List<CategoryResponse> expect = request().get("/categories")
                .withDocument("category/show/all")
                .build()
                .convertBodyToList(CategoryResponse.class);

        //then
        List<CategoryResponse> actual = List
                .of(TestDataLoader.BEER_CATEGORY, TestDataLoader.SOJU_CATEGORY)
                .stream()
                .map(CategoryResponse::create)
                .collect(Collectors.toList());

        assertThat(expect).usingRecursiveComparison().isEqualTo(actual);
    }
}
