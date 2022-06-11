package com.jujeol.drink.rds.repository;

import com.jujeol.drink.rds.entity.CategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CategoryRepository extends JpaRepository<CategoryEntity, Long> {
    Optional<CategoryEntity> findByKey(String key);
}
