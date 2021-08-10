package com.jujeol.drink.domain.repository;

import com.jujeol.drink.domain.Drink;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface DrinkRepository extends JpaRepository<Drink, Long>, DrinkCustomRepository {

    @Query("select d from Drink d join fetch d.category where d.id = :drinkId")
    Optional<Drink> findByIdWithFetch(Long drinkId);

    @Query("select d from Drink d where d.name.name = :drinkName")
    Optional<Drink> findByName(String drinkName);

    @Query(value = "select d from Drink d join fetch d.category where d.preferenceAvg >= 0 order by d.preferenceAvg desc")
    List<Drink> findDrinks(Pageable pageable);

    @Query(value = "select d from Drink d join fetch d.category where d.id not in (select p.drink.id from Preference p where p.member.id = :memberId) order by d.preferenceAvg desc")
    List<Drink> findDrinksForMember(Long memberId, Pageable pageable);
}
