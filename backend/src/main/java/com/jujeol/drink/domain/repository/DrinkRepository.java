package com.jujeol.drink.domain.repository;

import com.jujeol.drink.domain.Drink;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface DrinkRepository extends JpaRepository<Drink, Long>, DrinkCustomRepository {

    @Query("select d from Drink d where d.name.name = :drinkName")
    Optional<Drink> findByName(String drinkName);
}
