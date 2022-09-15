package com.jujeol.drink.rds.repository;

import com.jujeol.drink.rds.entity.DrinkEntity;
import java.util.Collection;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DrinkRepository extends JpaRepository<DrinkEntity, Long>, DrinkRepositoryCustom {

    List<DrinkEntity> findAllByIdIn(Collection<Long> drinkIds);
}
