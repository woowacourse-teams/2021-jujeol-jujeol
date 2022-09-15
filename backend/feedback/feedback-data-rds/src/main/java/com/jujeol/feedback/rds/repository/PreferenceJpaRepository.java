package com.jujeol.feedback.rds.repository;

import com.jujeol.feedback.domain.model.Preference;
import com.jujeol.feedback.rds.entity.PreferenceEntity;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PreferenceJpaRepository extends JpaRepository<PreferenceEntity, Long> {

    Optional<PreferenceEntity> findByDrinkIdAndMemberId(Long drinkId, Long memberId);

    Page<PreferenceEntity> findByMemberId(Long memberId, Pageable pageable);

    List<PreferenceEntity> findByMemberIdAndDrinkIdIn(Long memberId, List<Long> drinkIds);

    List<PreferenceEntity> findAllByDrinkId(Long drinkId);
}
