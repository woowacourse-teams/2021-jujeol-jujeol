package com.jujeol.drink.domain.repository;

import com.jujeol.drink.domain.Drink;
import java.util.List;

public interface DrinkCustomRepository {

    void batchInsert(List<Drink> drinks);
}
