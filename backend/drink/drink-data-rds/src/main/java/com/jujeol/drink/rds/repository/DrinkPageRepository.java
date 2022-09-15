package com.jujeol.drink.rds.repository;

import com.jujeol.drink.domain.model.Drink;
import com.jujeol.drink.domain.model.DrinkSort;
import com.jujeol.drink.rds.entity.DrinkEntity;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

@Deprecated // app 쪽에서 page 관련 의존성이 빠질 때가지만 잠시 사용
@Component
@RequiredArgsConstructor
public class DrinkPageRepository {

    private final DrinkRepository drinkRepository;

    @Transactional(readOnly = true)
    public Page<Drink> findAll(Pageable pageable) {
        return drinkRepository.findAll(pageable).map(DrinkEntity::toDomain);
    }

    @Transactional(readOnly = true)
    public Page<Drink> findAll(DrinkSort drinkSort, Pageable pageable) {
        return drinkRepository.findAllWithSort(pageable, drinkSort).map(DrinkEntity::toDomain);
    }

    @Transactional(readOnly = true)
    public Page<Drink> findByKeywords(List<String> keywords, Pageable pageable) {
        return drinkRepository.findByKeywords(keywords, pageable).map(DrinkEntity::toDomain);
    }

    @Transactional(readOnly = true)
    public Page<Drink> findByCategory(String category, Pageable pageable) {
        return drinkRepository.findAllByCategoryName(category, pageable).map(DrinkEntity::toDomain);
    }

    @Transactional(readOnly = true)
    public Page<Drink> findByCategory(DrinkSort drinkSort, String category, Pageable pageable) {
        if (StringUtils.hasText(category) && "ALL".equals(category)) {
            return findAll(drinkSort, pageable);
        }

        return drinkRepository.findAllByCategoryNameWithSort(category, pageable, drinkSort).map(DrinkEntity::toDomain);
    }

    @Transactional(readOnly = true)
    public Page<Drink> findAllSortByPreference(Pageable pageable) {
        return drinkRepository.findAll(
                                      PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(),
                                                     Sort.by("preferenceAvg")))
                              .map(DrinkEntity::toDomain);
    }
}
