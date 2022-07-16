package com.jujeol.drink.service;

import com.jujeol.drink.domain.model.Category;
import com.jujeol.drink.domain.reader.CategoryReader;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryReader categoryReader;

    @Transactional(readOnly = true)
    public List<Category> findCategories() {
        return categoryReader.findAll();
    }
}
