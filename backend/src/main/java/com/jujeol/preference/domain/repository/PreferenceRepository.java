package com.jujeol.preference.domain.repository;

import com.jujeol.preference.domain.Preference;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface PreferenceRepository extends JpaRepository<Preference, Long>, PreferenceCustomRepository {

    Optional<Preference> findByMemberIdAndDrinkId(Long memberId, Long drinkId);

    @Modifying
    void deleteByMemberIdAndDrinkId(Long memberId, Long drinkId);

    @Modifying
    void deleteByDrinkId(Long drinkId);

    @Query("select p from Preference p where p.member.id = :memberId and p.drink.id in :drinkIds")
    List<Preference> findByMemberWithDrinkIds(List<Long> drinkIds, Long memberId);
}
