package com.jujeol.drink.rds.repository;

import com.jujeol.drink.domain.model.Drink;
import com.jujeol.drink.rds.entity.DrinkEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

@Deprecated // app 쪽에서 page 관련 의존성이 빠질 때가지만 잠시 사용
@Component
@RequiredArgsConstructor
public class DrinkPageRepository {

    private final DrinkRepository drinkRepository;

    public Page<Drink> findAll(Pageable pageable) {
        return drinkRepository.findAll(pageable).map(DrinkEntity::toDomain);
    }
}
