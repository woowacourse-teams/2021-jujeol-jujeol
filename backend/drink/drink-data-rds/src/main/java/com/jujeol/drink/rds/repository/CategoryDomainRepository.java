package com.jujeol.drink.rds.repository;

import com.jujeol.drink.domain.model.Category;
import com.jujeol.drink.domain.usecase.CategoryRegisterUseCase;
import com.jujeol.drink.domain.usecase.DrinkRegisterUseCase;
import com.jujeol.drink.rds.entity.CategoryEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class CategoryDomainRepository implements
    CategoryRegisterUseCase.CategoryPort,
    DrinkRegisterUseCase.CategoryPort {

    private final CategoryRepository categoryRepository;

    @Override
    @Transactional
    public void createCategory(String name, String key) {
        CategoryEntity categoryEntity = CategoryEntity.builder()
            .key(key)
            .name(name)
            .build();
        categoryRepository.save(categoryEntity);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Category> findByKey(String key) {
        return categoryRepository.findByKey(key)
            .map(category -> Category.create(category.getId(), category.getName(), category.getKey()));
    }
}
