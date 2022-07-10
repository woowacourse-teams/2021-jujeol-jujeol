package com.jujeol.feedback.rds.repository;

import com.jujeol.feedback.domain.model.Preference;
import com.jujeol.feedback.domain.reader.PreferenceReader;
import com.jujeol.feedback.rds.entity.PreferenceEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class PreferenceDomainRepository implements PreferenceReader {

    private final PreferenceJpaRepository preferenceJpaRepository;

    @Override
    @Transactional(readOnly = true)
    public Optional<Preference> findByDrinkIdAndMemberId(Long drinkId, Long memberId) {
        return preferenceJpaRepository.findByDrinkIdAndMemberId(drinkId, memberId).map(PreferenceEntity::toDomain);
    }
}
