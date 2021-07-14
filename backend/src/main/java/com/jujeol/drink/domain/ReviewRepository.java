package com.jujeol.drink.domain;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    @Query("select r from Review r where r.drink.id = :drinkId")
    Page<Review> findAllByDrinkId(Long drinkId, Pageable pageable);
}
