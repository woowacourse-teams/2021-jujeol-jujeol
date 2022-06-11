package com.jujeol.drink.rds.repository;

import com.jujeol.drink.rds.entity.DrinkEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DrinkRepository extends JpaRepository<DrinkEntity, Long> {

}
