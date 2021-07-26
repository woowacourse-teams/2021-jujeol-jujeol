package com.jujeol.drink.domain.repository;

import com.jujeol.drink.domain.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
