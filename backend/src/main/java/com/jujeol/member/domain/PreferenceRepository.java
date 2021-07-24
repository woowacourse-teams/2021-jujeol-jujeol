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

    @Query("select distinct d from Preference p join p.drink d "
            + "where (select avg(p.rate) from p where p.drink = d) > 2")
    Page<Drink> findAllOrderByPreference(Pageable pageable);
}
