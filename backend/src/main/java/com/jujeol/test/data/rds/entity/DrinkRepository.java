package com.jujeol.test.data.rds.entity;

import com.jujeol.test.domain.model.obj.Drink;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DrinkRepository extends JpaRepository<Drink, Long> {
}
