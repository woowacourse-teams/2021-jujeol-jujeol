package com.jujeol.drink.domain.repository;

import com.jujeol.drink.domain.Drink;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DrinkRepository extends JpaRepository<Drink, Long>, DrinkCustomRepository {
}
