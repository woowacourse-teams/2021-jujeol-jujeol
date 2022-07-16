package com.jujeol.drink.rds.repository;

import com.jujeol.drink.domain.model.Category;
import com.jujeol.drink.domain.reader.CategoryReader;
import com.jujeol.drink.domain.usecase.CategoryRegisterUseCase;
import com.jujeol.drink.domain.usecase.DrinkRegisterUseCase;
import com.jujeol.drink.rds.entity.CategoryEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class CategoryDomainRepository implements
    CategoryReader,

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
    public boolean existsByKey(String key) {
        return categoryRepository.existsByKey(key);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsByName(String name) {
        return categoryRepository.existsByName(name);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Category> findByKey(String key) {
        return categoryRepository.findByKey(key).map(CategoryEntity::toDomain);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Category> findAll() {
        return categoryRepository.findAll().stream().map(CategoryEntity::toDomain).collect(Collectors.toList());
    }
}
