package com.jujeol.drink.domain.repository;

import com.jujeol.drink.domain.Drink;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface DrinkRepository extends JpaRepository<Drink, Long>, DrinkCustomRepository {

    @Query("select d from Drink d order by d.viewCount.viewCount desc")
    Page<Drink> findAllOrderByViewCount(Pageable pageable);

}
