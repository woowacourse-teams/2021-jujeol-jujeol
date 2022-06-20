package com.jujeol.drink.domain.service;

import com.jujeol.drink.DomainTestContext;
import com.jujeol.drink.domain.exception.DuplicateCategoryKeyException;
import com.jujeol.drink.domain.exception.DuplicateCategoryNameException;
import com.jujeol.drink.domain.usecase.CategoryRegisterUseCase;
import com.jujeol.drink.domain.usecase.command.CategoryRegisterCommand;
import com.jujeol.drink.rds.entity.CategoryEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class CategoryCommandExecutorTest extends DomainTestContext {

    @Autowired
    private CategoryCommandExecutor categoryCommandExecutor;

    @Nested
    @DisplayName("카테고리 등록")
    class CategoryRegisterUseCaseTest {

        private CategoryRegisterUseCase sut;

        @BeforeEach
        void setUp() {
            sut = categoryCommandExecutor;
        }

        @Test
        void 등록() {
            // given
            String key = "BEER";
            String name = "맥주";

            // when
            sut.register(CategoryRegisterCommand.create(name, key));

            // then
            CategoryEntity savedCategory = categoryRepository.findByKey(key).orElseThrow();
            assertThat(savedCategory.getId()).isNotNull();
            assertThat(savedCategory.getKey()).isEqualTo(key);
            assertThat(savedCategory.getName()).isEqualTo(name);
        }

        @Test
        void 중복키() {
            // given
            String key = "BEER";
            String name = "맥주";

            categoryRepository.save(CategoryEntity.builder().name("소주").key(key).build());

            // when, then
            assertThatThrownBy(() -> sut.register(CategoryRegisterCommand.create(name, key)))
                .isExactlyInstanceOf(DuplicateCategoryKeyException.class);
        }

        @Test
        void 중복이름() {
            // given
            String key = "BEER";
            String name = "맥주";

            categoryRepository.save(CategoryEntity.builder().name(name).key("SOJU").build());

            // when, then
            assertThatThrownBy(() -> sut.register(CategoryRegisterCommand.create(name, key)))
                .isExactlyInstanceOf(DuplicateCategoryNameException.class);
        }
    }
}