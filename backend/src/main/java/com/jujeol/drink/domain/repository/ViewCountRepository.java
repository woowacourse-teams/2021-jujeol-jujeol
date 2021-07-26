package com.jujeol.drink.domain.repository;

import com.jujeol.drink.domain.ViewCount;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ViewCountRepository extends JpaRepository<ViewCount, Long> {

    Optional<ViewCount> findByDrinkId(Long drinkId);
}
