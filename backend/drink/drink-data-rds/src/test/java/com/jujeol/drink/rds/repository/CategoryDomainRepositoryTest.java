package com.jujeol.drink.rds.repository;

import com.jujeol.drink.RdsTestContext;
import com.jujeol.drink.rds.entity.CategoryEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;

class CategoryDomainRepositoryTest extends RdsTestContext {

    @Autowired
    private CategoryDomainRepository sut;

    @Test
    void createCategory() {
        // given
        String categoryName = "맥주";
        String categoryKey = "BEER";

        // when
        sut.createCategory(categoryName, categoryKey);

        // then
        CategoryEntity categoryEntity = categoryRepository.findAll()
            .stream()
            .findAny()
            .orElseThrow();

        assertThat(categoryEntity.getId()).isNotNull();
        assertThat(categoryEntity.getName()).isEqualTo(categoryName);
        assertThat(categoryEntity.getKey()).isEqualTo(categoryKey);
    }
}