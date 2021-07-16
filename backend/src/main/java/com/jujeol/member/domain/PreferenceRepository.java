package com.jujeol.member.domain;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface PreferenceRepository extends JpaRepository<Preference, Long> {

    @Query("select p from Preference p where p.member.id = :memberId and p.drink.id = :drinkId")
    Optional<Preference> findByMemberIdAndDrinkId(Long memberId, Long drinkId);

    @Modifying
    @Query("delete from Preference p where p.member.id = :memberId and p.drink.id = :drinkId")
    void deleteByMemberIdAndDrinkId(Long memberId, Long drinkId);
}
