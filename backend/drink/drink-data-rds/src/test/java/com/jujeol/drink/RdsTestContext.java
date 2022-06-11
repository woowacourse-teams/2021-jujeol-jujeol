package com.jujeol.drink;

import com.jujeol.drink.rds.repository.CategoryRepository;
import com.jujeol.drink.rds.repository.DrinkRepository;
import org.junit.jupiter.api.AfterEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("local")
public abstract class RdsTestContext {

    @Autowired
    protected CategoryRepository categoryRepository;

    @Autowired
    protected DrinkRepository drinkRepository;

    @AfterEach
    void tearDown() {
        drinkRepository.deleteAll();
        categoryRepository.deleteAll();
    }
}
