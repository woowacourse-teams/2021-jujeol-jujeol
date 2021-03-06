package com.jujeol.drink.category.application;

import com.jujeol.drink.category.application.dto.CategoryDto;
import com.jujeol.drink.category.domain.CategoryRepository;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
@Transactional(readOnly = true)
public class CategoryService {

    private CategoryRepository categoryRepository;

    public List<CategoryDto> showCategories() {
        return categoryRepository.findAll()
                .stream()
                .map(CategoryDto::create)
                .collect(Collectors.toList());
    }
}
