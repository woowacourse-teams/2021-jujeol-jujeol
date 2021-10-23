package com.jujeol.drink.drink.domain.repository;

import com.jujeol.drink.drink.domain.Drink;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface DrinkRepository extends JpaRepository<Drink, Long>, DrinkCustomRepository {

    @Query("select d from Drink d join fetch d.category where d.id = :drinkId")
    Optional<Drink> findByIdWithFetch(Long drinkId);

    @Query("select d from Drink d where d.name.name = :drinkName")
    Optional<Drink> findByName(String drinkName);

    @Query(value = "select d from Drink d join fetch d.category order by d.preferenceAvg desc")
    List<Drink> findDrinks(Pageable pageable);

    @Query("select d from Drink d join fetch d.category "
            + "where (d.id in (select p.drink.id from Preference p where p.member.id = :memberId and p.rate > 3) "
            + "or d.id not in (select p.drink.id from Preference p where p.member.id = :memberId)) and d.category.key = :category order by d.preferenceAvg desc")
    List<Drink> findDrinksForMember(Long memberId, Pageable pageable, String category);

    @Query("select d from Drink d join fetch d.category "
            + "where d.id in (select p.drink.id from Preference p where p.member.id = :memberId and p.rate > 3) "
            + "or d.id not in (select p.drink.id from Preference p where p.member.id = :memberId) order by d.preferenceAvg desc")
    List<Drink> findDrinksForMember(Long memberId, Pageable pageable);

    @Query(value = "select d from Drink d join fetch d.category c where c.key = :category order by d.preferenceAvg desc"
            ,countQuery = "select count(d) from Drink d where d.category.key = :category")
    Page<Drink> findAllByCategory(String category, Pageable pageable);

    @Query(value = "select d from Drink d join fetch d.category order by d.preferenceAvg desc",
    countQuery = "select count(d) from Drink d")
    Page<Drink> findAllSortByPreference(Pageable pageable);

    @Query(value = "select d from Drink d where d.name.name like %:keyword% or d.englishName.englishName like %:keyword%")
    Page<Drink> findWithKeyword(String keyword, Pageable pageable);
}
