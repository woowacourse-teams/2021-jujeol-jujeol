package com.jujeol.preference.domain;

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

    @Query(value = "Select p From Preference p join fetch p.drink d join fetch p.member join fetch d.category where p.member.id = :memberId order by p.createdAt desc",
            countQuery = "Select count(p) From Preference p where p.member.id = :memberId")
    Page<Preference> findByMemberIdOrderByCreatedAtDesc(Long memberId, Pageable pageable);
}
