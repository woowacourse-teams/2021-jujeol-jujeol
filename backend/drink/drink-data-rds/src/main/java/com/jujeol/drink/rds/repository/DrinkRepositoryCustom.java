package com.jujeol.drink.rds.repository;

import com.jujeol.drink.rds.entity.DrinkEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface DrinkRepositoryCustom {

    Page<DrinkEntity> findByKeywords(List<String> keywords, Pageable pageable);
}
