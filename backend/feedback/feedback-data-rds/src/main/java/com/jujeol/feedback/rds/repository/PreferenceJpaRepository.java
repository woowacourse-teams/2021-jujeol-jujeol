package com.jujeol.feedback.rds.repository;

import com.jujeol.feedback.rds.entity.PreferenceEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PreferenceJpaRepository extends JpaRepository<PreferenceEntity, Long> {

    Optional<PreferenceEntity> findByDrinkIdAndMemberId(Long drinkId, Long memberId);

    Page<PreferenceEntity> findByMemberId(Long memberId, Pageable pageable);
}
