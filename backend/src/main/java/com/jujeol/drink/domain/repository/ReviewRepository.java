package com.jujeol.drink.domain.repository;

import com.jujeol.drink.domain.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    Page<Review> findAllByDrinkId(Long drinkId, Pageable pageable);
}
