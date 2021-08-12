package com.jujeol.drink.category.domain;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long>,
        CategoryCustomRepository {

    Optional<Category> findByKey(String key);
}
