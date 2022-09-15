package com.jujeol.drink.domain.reader;

import com.jujeol.drink.domain.model.Drink;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface DrinkReader {

    Optional<Drink> findById(Long id);

    List<Drink> findAllByDrinkIdIn(Collection<Long> drinkIds);
}
