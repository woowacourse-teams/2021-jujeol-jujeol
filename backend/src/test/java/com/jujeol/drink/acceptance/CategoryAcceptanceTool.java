package com.jujeol.drink.acceptance;

import com.jujeol.drink.domain.Category;
import com.jujeol.drink.domain.repository.CategoryRepository;
import java.util.Arrays;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.test.context.ActiveProfiles;

@Component
@ActiveProfiles("test")
public class CategoryAcceptanceTool {

    @Autowired
    private CategoryRepository categoryRepository;

    public void 기본_카테고리_저장() {
        기본_카테고리().forEach(category -> categoryRepository.save(category));
    }

    public List<Category> 기본_카테고리() {
        return Arrays.asList(Category.create("맥주", "BEER"), Category.create("소주", "SOJU"));
    }
}
