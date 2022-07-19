package com.jujeol.drink.rds.repository;

import com.jujeol.drink.rds.entity.DrinkEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.nio.channels.FileChannel;
import java.util.List;

public interface DrinkRepositoryCustom {

    Page<DrinkEntity> findByKeywords(List<String> keywords, Pageable pageable);

    Page<DrinkEntity> findAllByCategoryName(String category, Pageable pageable);

    Page<DrinkEntity> findAllWithSort(Pageable pageable);

    Page<DrinkEntity> findAllByCategoryNameWithSort(String category, Pageable pageable);
}
