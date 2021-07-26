package com.jujeol.member.domain;

import com.jujeol.drink.domain.Drink;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface PreferenceRepository extends JpaRepository<Preference, Long> {

    Optional<Preference> findByMemberIdAndDrinkId(Long memberId, Long drinkId);

    @Modifying
    void deleteByMemberIdAndDrinkId(Long memberId, Long drinkId);

    @Query("SELECT AVG(p.rate) FROM Preference p WHERE p.drink.id = :drinkId")
    Optional<Double> averageOfPreferenceRate(Long drinkId);

    @Query(value = ""
            + "select p.drink from Preference p "
            + "inner join Drink d on d.id = p.drink.id "
            + "where p.member.id = :memberId "
            + "order by p.createdAt desc",
            countQuery = ""
                    + "select p.drink from Preference p "
                    + "inner join Drink d on d.id = p.drink.id "
                    + "where p.member.id = :memberId "
                    + "order by p.createdAt desc")
    Page<Drink> findDrinksOfMineWithPreference(Long memberId, Pageable pageable);
}
