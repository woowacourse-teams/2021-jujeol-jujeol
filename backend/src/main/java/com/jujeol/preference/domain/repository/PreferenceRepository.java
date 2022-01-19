package com.jujeol.preference.domain.repository;

import com.jujeol.preference.domain.Preference;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;

import java.util.Optional;

public interface PreferenceRepository extends JpaRepository<Preference, Long>, PreferenceCustomRepository {

    Optional<Preference> findByMemberIdAndDrinkId(Long memberId, Long drinkId);

    @Modifying
    void deleteByMemberIdAndDrinkId(Long memberId, Long drinkId);

    @Modifying
    void deleteByDrinkId(Long drinkId);
}
