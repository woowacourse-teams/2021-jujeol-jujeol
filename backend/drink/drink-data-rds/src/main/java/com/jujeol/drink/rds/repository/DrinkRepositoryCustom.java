package com.jujeol.drink.rds.repository;

import com.jujeol.drink.domain.model.DrinkSort;
import com.jujeol.drink.rds.entity.DrinkEntity;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface DrinkRepositoryCustom {

    Page<DrinkEntity> findByKeywords(List<String> keywords, Pageable pageable);

    Page<DrinkEntity> findAllByCategoryName(String category, Pageable pageable);

    Page<DrinkEntity> findAllWithSort(Pageable pageable, DrinkSort drinkSort);

    Page<DrinkEntity> findAllByCategoryNameWithSort(String category, Pageable pageable, DrinkSort drinkSort);
}
