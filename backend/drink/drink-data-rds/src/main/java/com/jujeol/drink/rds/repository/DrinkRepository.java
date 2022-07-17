package com.jujeol.drink.rds.repository;

import com.jujeol.drink.rds.entity.DrinkEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;

public interface DrinkRepository extends JpaRepository<DrinkEntity, Long>, DrinkRepositoryCustom {

    List<DrinkEntity> findAllByDrinkIdIn(Collection<Long> drinkIds);
}
