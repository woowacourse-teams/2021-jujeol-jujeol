package com.jujeol.drink.drink.domain.repository;

import com.jujeol.drink.drink.domain.Drink;
import java.util.List;

public interface DrinkCustomRepository {

    void batchInsert(List<Drink> drinks);

    List<Drink> findByIds(List<Long> iDs);
}
